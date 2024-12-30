-- Drop the custom type if it exists
DROP TYPE IF EXISTS rolePengguna CASCADE;

-- Drop foreign key constraints and triggers first, if any
DROP TRIGGER IF EXISTS trigger_save_update ON Setlist;
DROP TRIGGER IF EXISTS trigger_save_delete ON Setlist;

-- Drop tables using CASCADE to ensure dependent objects are dropped
DROP TABLE IF EXISTS Setlist_lagu CASCADE;
DROP TABLE IF EXISTS SetlistHistory CASCADE;
DROP TABLE IF EXISTS Setlist CASCADE;
DROP TABLE IF EXISTS Komentar CASCADE;
DROP TABLE IF EXISTS Lagu CASCADE;
DROP TABLE IF EXISTS Album CASCADE;
DROP TABLE IF EXISTS Artis CASCADE;
DROP TABLE IF EXISTS Show CASCADE;
DROP TABLE IF EXISTS Lokasi CASCADE;
DROP TABLE IF EXISTS Kota CASCADE;
DROP TABLE IF EXISTS Negara CASCADE;
DROP TABLE IF EXISTS Pengguna CASCADE;

CREATE TYPE rolePengguna AS ENUM ('admin', 'pengguna');

CREATE TABLE Pengguna (
    username VARCHAR(255) PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL,
    nama VARCHAR(255) NOT NULL,
    role rolePengguna NOT NULL DEFAULT 'pengguna'
);

CREATE TABLE Negara(
	idNegara SERIAL PRIMARY KEY,
	namaNegara VARCHAR(255)
);

CREATE TABLE Kota(
	idKota SERIAL PRIMARY KEY,
	namaKota VARCHAR(255),
	idNegara INT REFERENCES Negara(idNegara) ON DELETE CASCADE
);

CREATE TABLE Lokasi(
    idLokasi SERIAL PRIMARY KEY,
    namaLokasi VARCHAR(255) NOT NULL,
	alamatLokasi TEXT,
	idKota INT REFERENCES Kota(idKota) ON DELETE CASCADE
);

CREATE TABLE Show(
    idShow SERIAL PRIMARY KEY,
    namaShow VARCHAR(255),
    idLokasi INT REFERENCES Lokasi(idLokasi)
);

CREATE TABLE Artis (
    idArtis SERIAL PRIMARY KEY,
    namaArtis VARCHAR(255) NOT NULL,
    urlGambarArtis VARCHAR(255)
);

CREATE TABLE Album(
    idAlbum SERIAL PRIMARY KEY,
    namaAlbum VARCHAR(255) NOT NULL,
    release_date DATE,
	idArtis INT REFERENCES Artis(idArtis),
	urlGambarAlbum VARCHAR(255)
);

CREATE TABLE Lagu(
    idLagu SERIAL PRIMARY KEY,
    idAlbum INT REFERENCES Album(idAlbum) ON DELETE CASCADE,
    namaLagu VARCHAR(255) NOT NULL,
    duration INT,
	idArtis INT REFERENCES Artis(idArtis),
    urlGambarLagu VARCHAR(255)
);

CREATE TABLE Setlist(
    idSetlist SERIAL PRIMARY KEY,
    namaSetlist VARCHAR(255) NOT NULL,
    tanggal TIMESTAMP,
    urlBukti VARCHAR(255),
    idShow INT REFERENCES Show(idShow) ON DELETE CASCADE
);

CREATE TABLE SetlistHistory (
    idHistory SERIAL PRIMARY KEY,
    idSetlist INT,
    namaSetlist VARCHAR(255),
    tanggal TIMESTAMP,
    urlBukti VARCHAR(255),
    idShow INT,
    action VARCHAR(10),
    tanggalDiubah TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Setlist_lagu(
    idSetlist INT REFERENCES Setlist(idSetlist) ON DELETE CASCADE,
    idLagu INT REFERENCES Lagu(idLagu) ON DELETE CASCADE,
	idArtis INT REFERENCES Artis(idArtis),
    trackNumber INT,
    PRIMARY KEY (idSetlist, idLagu)
);

CREATE TABLE Komentar(
    idKomentar SERIAL PRIMARY KEY,
    username VARCHAR(255) REFERENCES Pengguna(username),
    idSetlist INT REFERENCES Setlist(idSetlist),
    komentar TEXT NOT NULL,
    tanggal TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION save_update_to_history() 
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO SetlistHistory (idSetlist, namaSetlist, tanggal, urlBukti, idShow, action)
    VALUES (OLD.idSetlist, OLD.namaSetlist, OLD.tanggal, OLD.urlBukti, OLD.idShow, 'UPDATE');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_save_update
AFTER UPDATE ON Setlist
FOR EACH ROW
EXECUTE FUNCTION save_update_to_history();


CREATE OR REPLACE FUNCTION save_delete_to_history() 
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO SetlistHistory (idSetlist, namaSetlist, tanggal, urlBukti, idShow, action)
    VALUES (OLD.idSetlist, OLD.namaSetlist, OLD.tanggal, OLD.urlBukti, OLD.idShow, 'DELETE');
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_save_delete
AFTER DELETE ON Setlist
FOR EACH ROW
EXECUTE FUNCTION save_delete_to_history();

-- Insert into Pengguna
INSERT INTO Pengguna (username, email, password, nama, role) VALUES
('user1', 'user1@example.com', 'password1', 'User One', 'pengguna'),
('user2', 'user2@example.com', 'password2', 'User Two', 'pengguna'),
('user3', 'user3@example.com', 'password3', 'User Three', 'pengguna'),
('user4', 'user4@example.com', 'password4', 'User Four', 'pengguna'),
('user5', 'user5@example.com', 'password5', 'User Five', 'pengguna'),
('user6', 'user6@example.com', 'password6', 'User Six', 'pengguna'),
('user7', 'user7@example.com', 'password7', 'User Seven', 'pengguna'),
('user8', 'user8@example.com', 'password8', 'User Eight', 'pengguna'),
('user9', 'user9@example.com', 'password9', 'User Nine', 'pengguna'),
('user10', 'user10@example.com', 'password10', 'User Ten', 'pengguna'),
('user11', 'user11@example.com', 'password11', 'User Eleven', 'pengguna'),
('user12', 'user12@example.com', 'password12', 'User Twelve', 'pengguna'),
('user13', 'user13@example.com', 'password13', 'User Thirteen', 'pengguna'),
('user14', 'user14@example.com', 'password14', 'User Fourteen', 'pengguna'),
('user15', 'user15@example.com', 'password15', 'User Fifteen', 'pengguna'),
('user16', 'user16@example.com', 'password16', 'User Sixteen', 'pengguna'),
('user17', 'user17@example.com', 'password17', 'User Seventeen', 'pengguna'),
('user18', 'user18@example.com', 'password18', 'User Eighteen', 'pengguna'),
('user19', 'user19@example.com', 'password19', 'User Nineteen', 'pengguna'),
('user20', 'user20@example.com', 'password20', 'User Twenty', 'pengguna');

-- Insert into Negara
INSERT INTO Negara (namaNegara) VALUES 
('Indonesia'), ('United States'), ('Canada'), ('United Kingdom'), ('Australia'),
('Germany'), ('France'), ('Japan'), ('South Korea'), ('Brazil'),
('India'), ('China'), ('Russia'), ('Mexico'), ('Italy'),
('Spain'), ('Argentina'), ('Netherlands'), ('South Africa'), ('Egypt'), ('Turkey');

-- Insert into Kota
INSERT INTO Kota (namaKota, idNegara) VALUES
('Jakarta', 1), ('New York', 2), ('Toronto', 3), ('London', 4), ('Sydney', 5),
('Berlin', 6), ('Paris', 7), ('Tokyo', 8), ('Seoul', 9), ('Sao Paulo', 10),
('Mumbai', 11), ('Beijing', 12), ('Moscow', 13), ('Mexico City', 14), ('Rome', 15),
('Madrid', 16), ('Buenos Aires', 17), ('Amsterdam', 18), ('Cape Town', 19), ('Cairo', 20);

-- Insert into Lokasi
INSERT INTO Lokasi (namaLokasi, alamatLokasi, idKota) VALUES
('National Stadium', 'Jl. Gelora 1, Jakarta', 1), ('Madison Square Garden', 'New York, NY', 2), 
('Air Canada Centre', 'Toronto, Canada', 3), ('Wembley Stadium', 'London, UK', 4), 
('Sydney Opera House', 'Sydney, Australia', 5), ('Olympiastadion', 'Berlin, Germany', 6), 
('Parc des Princes', 'Paris, France', 7), ('Tokyo Dome', 'Tokyo, Japan', 8), 
('Seoul World Cup Stadium', 'Seoul, South Korea', 9), ('Morumbi Stadium', 'Sao Paulo, Brazil', 10),
('Wankhede Stadium', 'Mumbai, India', 11), ('Bird’s Nest Stadium', 'Beijing, China', 12), 
('Luzhniki Stadium', 'Moscow, Russia', 13), ('Estadio Azteca', 'Mexico City, Mexico', 14), 
('Coliseum', 'Rome, Italy', 15), ('Camp Nou', 'Barcelona, Spain', 16), 
('La Bombonera', 'Buenos Aires, Argentina', 17), ('Johan Cruijff Arena', 'Amsterdam, Netherlands', 18),
('Newlands Cricket Ground', 'Cape Town, South Africa', 19), ('Cairo International Stadium', 'Cairo, Egypt', 20);

-- Insert into Show
INSERT INTO Show (namaShow, idLokasi) VALUES
('Rock Concert', 1), ('Pop Show', 2), ('Classical Music Night', 3), ('Football Match', 4), 
('Musical Show', 5), ('Opera Night', 6), ('Rock Fest', 7), ('Jazz Concert', 8), 
('K-pop Concert', 9), ('World Cup Final', 10), ('Bollywood Night', 11), 
('World Track and Field', 12), ('Super Bowl', 13), ('European Football League', 14), 
('U2 World Tour', 15), ('Latin Concert', 16), ('Carnival Festival', 17), ('Eurovision', 18),
('SA Rugby Match', 19), ('Africa Music Festival', 20);

-- Insert into Artis
INSERT INTO Artis(namaArtis, urlGambarArtis) VALUES
('Shawn Mendes', '/artis/shawnMendes.png'),
('Selena Gomez', '/artis/selena.png'),
('Abel Makkonen', '/artis/abel.png'),
('Halsey', '/artis/halsey.png'),
('Billie Eilish', '/artis/billie.png'),
('Justin Bieber', '/artis/justin.png'),
('Ariana Grande', '/artis/ariana.png'),
('Ed Sheeran', '/artis/edsheeran.png'),
('Dua Lipa', '/artis/dualipa.png'),
('BTS', '/artis/bts.png'),
('Calum Scott', '/artis/calumscott.png'),
('Adele', '/artis/adele.png'),
('Alan Walker', '/artis/alanwalker.jpg'),
('Lauv', '/artis/lauv.jpeg'),
('James Arthur', '/artis/jamesarthur.png'),
('Bruno Mars', '/artis/brunomars.jpeg'),
('Troye Sivan', '/artis/troyesivan.jpg'),
('Taylor Swift', '/artis/taylorswift.png'),
('Sam Smith', '/artis/samsmith.jpg'),
('Jamie Miller', '/artis/jamie_miller.jpeg');

-- Insert into Album
INSERT INTO Album (namaAlbum, release_date, idArtis, urlGambarAlbum) VALUES
('Handwritten', '2015-04-14', 1, '/album/shawn_1.jpeg'), ('Illuminate', '2016-09-23', 1, '/album/shawn_2.jpeg'),
('Rare', '2020-04-09', 2, '/album/selena_1.jpeg'), ('Stars Dance', '2013-01-01', 2, '/album/selena_2.jpeg'),
('Starboy', '2016-11-25', 3, '/album/abel_1.jpeg'), ('After Hours', '2020-03-20', 3, '/album/abel_2.jpeg'),
('Badlands', '2015-08-28', 4, '/album/halsey_1.jpeg'), ('Hopeless Fountain Kingdom', '2017-06-02', 4, '/album/halsey_2.jpeg'),
('When We All Fall Asleep, Where Do We Go?', '2019-03-29', 5, '/album/billie_1.jpeg'), ('Happier Than Ever', '2021-07-30', 5, '/album/billie_2.jpeg'),
('Purpose', '2015-11-13', 6, '/album/justin_1.jpeg'), ('Justice', '2021-03-19', 6, '/album/justin_2.jpeg'),
('Sweetener', '2018-08-17', 7, '/album/ariana_1.jpeg'), ('Thank U, Next', '2019-02-08', 7, '/album/ariana_2.jpeg'),
('Divide', '2017-03-03', 8, '/album/ed_1.jpeg'), ('Equals', '2021-10-29', 8, '/album/ed_2.jpeg'),
('Dua Lipa', '2017-06-02', 9, '/album/dua_1.jpeg'), ('Future Nostalgia', '2020-03-27', 9, '/album/dua_2.jpeg'),
('Map of the Soul: 7', '2020-02-21', 10, '/album/bts_1.jpeg'), ('BE', '2020-11-20', 10, '/album/bts_2.jpeg'),
('Only Human', '2018-03-09', 11, '/album/calum_1.jpeg'), ('Bridges', '2022-06-17', 11, '/album/calum_2.jpeg'),
('25', '2015-11-20', 12, '/album/adele_1.jpeg'), ('30', '2021-11-19', 12, '/album/adele_2.jpeg'),
('Different World', '2018-12-14', 13, '/album/alan_1.jpeg'), ('World of Walker', '2021-11-26', 13, '/album/alan_2.jpeg'),
('I Met You When I Was 18', '2018-05-31', 14, '/album/lauv_1.jpeg'), ('How Im Feeling', '2020-03-06', 14, '/album/lauv_2.jpeg'),
('Back from the Edge', '2016-10-28', 15, '/album/james_1.jpeg'), ('You', '2019-10-18', 15, '/album/james_2.jpeg'),
('24K Magic', '2016-11-18', 16, '/album/bruno_1.jpeg'), ('An Evening with Silk Sonic', '2021-11-12', 16, '/album/bruno_2.jpeg'),
('Blue Neighbourhood', '2015-12-04', 17, '/album/troye_1.jpeg'), ('Bloom', '2018-08-31', 17, '/album/troye_2.jpeg'),
('1989', '2014-10-27', 18, '/album/taylor_1.jpeg'), ('Lover', '2019-08-23', 18, '/album/taylor_2.jpeg'),
('The Thrill of It All', '2017-11-03', 19, '/album/sam_1.jpeg'), ('Love Goes', '2020-10-30', 19, '/album/sam_2.jpeg'),
('Broken Memories', '2022-04-29', 20, '/album/jamie_1.jpeg'), ('The Things I Left Unsaid', '2023-09-15', 20, '/album/jamie_2.jpeg');


-- Insert into Lagu
INSERT INTO Lagu (idAlbum, namaLagu, duration, idArtis, urlGambarLagu) VALUES
-- Shawn Mendes
(1, 'Life of the Party', 214, 1, '/album/shawn_1.jpeg'),
(1, 'Stitches', 206, 1, '/album/shawn_1.jpeg'),
(2, 'Mercy', 208, 1, '/album/shawn_2.jpeg'),
(2, 'Treat You Better', 187, 1, '/album/shawn_2.jpeg'),

-- Selena Gomez
(3, 'Lose You to Love Me', 207, 2, '/album/selena_1.jpeg'),
(3, 'Rare', 220, 2, '/album/selena_1.jpeg'),
(4, 'Come & Get It', 231, 2, '/album/selena_2.jpeg'),
(4, 'Slow Down', 210, 2, '/album/selena_2.jpeg'),

-- Abel Makkonen (The Weeknd)
(5, 'Starboy', 230, 3, '/album/abel_1.jpeg'),
(5, 'Party Monster', 249, 3, '/album/abel_1.jpeg'),
(6, 'Blinding Lights', 200, 3, '/album/abel_2.jpeg'),
(6, 'Save Your Tears', 215, 3, '/album/abel_2.jpeg'),

-- Halsey
(7, 'Colors', 250, 4, '/album/halsey_1.jpeg'),
(7, 'New Americana', 198, 4, '/album/halsey_1.jpeg'),
(8, 'Now or Never', 214, 4, '/album/halsey_2.jpeg'),
(8, 'Bad at Love', 181, 4, '/album/halsey_2.jpeg'),

-- Billie Eilish
(9, 'Bad Guy', 194, 5, '/album/billie_1.jpeg'),
(9, 'When the Party\s Over', 196, 5, '/album/billie_1.jpeg'),
(10, 'Happier Than Ever', 298, 5, '/album/billie_2.jpeg'),
(10, 'Your Power', 245, 5, '/album/billie_2.jpeg'),

-- Justin Bieber
(11, 'Love Yourself', 234, 6, '/album/justin_1.jpeg'),
(11, 'Sorry', 200, 6, '/album/justin_1.jpeg'),
(12, 'Peaches', 198, 6, '/album/justin_2.jpeg'),
(12, 'Holy', 212, 6, '/album/justin_2.jpeg'),

-- Ariana Grande
(13, 'No Tears Left to Cry', 205, 7, '/album/ariana_1.jpeg'),
(13, 'God Is a Woman', 197, 7, '/album/ariana_1.jpeg'),
(14, '7 Rings', 179, 7, '/album/ariana_2.jpeg'),
(14, 'Thank U, Next', 207, 7, '/album/ariana_2.jpeg'),

-- Ed Sheeran
(15, 'Shape of You', 234, 8, '/album/ed_1.jpeg'),
(15, 'Castle on the Hill', 261, 8, '/album/ed_1.jpeg'),
(16, 'Bad Habits', 230, 8, '/album/ed_2.jpeg'),
(16, 'Shivers', 207, 8, '/album/ed_2.jpeg'),

-- Dua Lipa
(17, 'New Rules', 225, 9, '/album/dua_1.jpeg'),
(17, 'IDGAF', 217, 9, '/album/dua_1.jpeg'),
(18, 'Don\t Start Now', 183, 9, '/album/dua_2.jpeg'),
(18, 'Levitating', 203, 9, '/album/dua_2.jpeg'),

-- BTS
(19, 'Boy with Luv', 229, 10, '/album/bts_1.jpeg'),
(19, 'Make It Right', 226, 10, '/album/bts_1.jpeg'),
(20, 'Life Goes On', 207, 10, '/album/bts_2.jpeg'),
(20, 'Dynamite', 199, 10, '/album/bts_2.jpeg'),

-- Calum Scott
(21, 'You Are the Reason', 204, 11, '/album/calum_1.jpeg'),
(21, 'No Matter What', 239, 11, '/album/calum_1.jpeg'),
(22, 'Biblical', 219, 11, '/album/calum_2.jpeg'),
(22, 'Heaven', 195, 11, '/album/calum_2.jpeg'),

-- Adele
(23, 'Hello', 295, 12, '/album/adele_1.jpeg'),
(23, 'When We Were Young', 290, 12, '/album/adele_1.jpeg'),
(24, 'Easy On Me', 225, 12, '/album/adele_2.jpeg'),
(24, 'Oh My God', 225, 12, '/album/adele_2.jpeg'),

-- Alan Walker
(25, 'Faded', 212, 13, '/album/alan_1.jpeg'),
(25, 'Alone', 161, 13, '/album/alan_1.jpeg'),
(26, 'Sweet Dreams', 195, 13, '/album/alan_2.jpeg'),
(26, 'Paradise', 218, 13, '/album/alan_2.jpeg'),

-- Lauv
(27, 'I Like Me Better', 197, 14, '/album/lauv_1.jpeg'),
(27, 'Paris in the Rain', 205, 14, '/album/lauv_1.jpeg'),
(28, 'Modern Loneliness', 252, 14, '/album/lauv_2.jpeg'),
(28, 'Tattoos Together', 188, 14, '/album/lauv_2.jpeg'),

-- James Arthur
(29, 'Say You Won\t Let Go', 211, 15, '/album/james_1.jpeg'),
(29, 'Can I Be Him', 245, 15, '/album/james_1.jpeg'),
(30, 'Falling Like the Stars', 214, 15, '/album/james_2.jpeg'),
(30, 'Quite Miss Home', 229, 15, '/album/james_2.jpeg'),

-- Bruno Mars
(31, '24K Magic', 226, 16, '/album/bruno_1.jpeg'),
(31, 'That\s What I Like', 206, 16, '/album/bruno_1.jpeg'),
(32, 'Leave the Door Open', 242, 16, '/album/bruno_2.jpeg'),
(32, 'Skate', 203, 16, '/album/bruno_2.jpeg'),

-- Troye Sivan
(33, 'Youth', 197, 17, '/album/troye_1.jpeg'),
(33, 'Wild', 228, 17, '/album/troye_1.jpeg'),
(34, 'My My My!', 205, 17, '/album/troye_2.jpeg'),
(34, 'Bloom', 224, 17, '/album/troye_2.jpeg'),

-- Taylor Swift
(35, 'Blank Space', 231, 18, '/album/taylor_1.jpeg'),
(35, 'Style', 231, 18, '/album/taylor_1.jpeg'),
(36, 'Lover', 221, 18, '/album/taylor_2.jpeg'),
(36, 'You Need to Calm Down', 171, 18, '/album/taylor_2.jpeg'),

-- Sam Smith
(37, 'Too Good at Goodbyes', 201, 19, '/album/sam_1.jpeg'),
(37, 'Pray', 221, 19, '/album/sam_1.jpeg'),
(38, 'Diamonds', 214, 19, '/album/sam_2.jpeg'),
(38, 'Love Goes', 221, 19, '/album/sam_2.jpeg'),

-- Jamie Miller
(39, 'Here\s Your Perfect', 170, 20, '/album/jamie_1.jpeg'),
(39, 'It Is What It Is', 202, 20, '/album/jamie_1.jpeg'),
(40, 'No Matter What', 214, 20, '/album/jamie_2.jpeg'),
(40, 'The Things I Left Unsaid', 225, 20, '/album/jamie_2.jpeg');

INSERT INTO Setlist (namaSetlist, tanggal, urlBukti, idShow) VALUES
('Setlist 1', '2022-01-01 20:00:00', '/images/setlist1.jpg', 1),
('Setlist 2', '2022-02-01 20:00:00', '/assets/topArtist/logo1.png', 2),
('Setlist 3', '2022-03-01 20:00:00', '/images/setlist3.jpg', 3),
('Setlist 4', '2022-04-01 20:00:00', '/assets/topArtist/logo1.png', 4),
('Setlist 5', '2022-05-01 20:00:00', '/images/setlist5.jpg', 5),
('Setlist 6', '2022-06-01 20:00:00', '/assets/topArtist/logo1.png', 6),
('Setlist 7', '2022-07-01 20:00:00', '/images/setlist7.jpg', 7),
('Setlist 8', '2022-08-01 20:00:00', '/assets/topArtist/logo1.png', 8),
('Setlist 9', '2022-09-01 20:00:00', '/images/setlist9.jpg', 9),
('Setlist 10', '2022-10-01 20:00:00', '/assets/topArtist/logo1.png', 10),
('Setlist 11', '2022-11-01 20:00:00', '/images/setlist11.jpg', 11),
('Setlist 12', '2022-12-01 20:00:00', '/assets/topArtist/logo1.png', 12),
('Setlist 13', '2023-01-01 20:00:00', '/images/setlist13.jpg', 13),
('Setlist 14', '2023-02-01 20:00:00', '/assets/topArtist/logo1.png', 14),
('Setlist 15', '2023-03-01 20:00:00', '/images/setlist15.jpg', 15),
('Setlist 16', '2023-04-01 20:00:00', '/assets/topArtist/logo1.png', 16),
('Setlist 17', '2023-05-01 20:00:00', '/images/setlist17.jpg', 17),
('Setlist 18', '2023-06-01 20:00:00', '/assets/topArtist/logo1.png', 18),
('Setlist 19', '2023-07-01 20:00:00', '/images/setlist19.jpg', 19),
('Setlist 20', '2023-08-01 20:00:00', '/assets/topArtist/logo1.png', 20);

INSERT INTO Setlist_lagu (idSetlist, idLagu, idArtis, trackNumber) VALUES
(1, 1, 1, 1), (2, 3, 1, 2), (3, 5, 1, 3), (4, 7, 1, 4),
(5, 9, 2, 5), (6, 11, 2, 6), (7, 13, 2, 7), (8, 15, 3, 8),
(9, 17, 3, 9), (10, 19, 3, 10), (11, 21, 3, 11), (12, 23, 4, 12),
(13, 25, 4, 13), (14, 27, 4, 14), (15, 29, 5, 15), (16, 31, 5, 16),
(17, 33, 5, 17), (18, 35, 6, 18), (19, 37, 6, 19), (20, 39, 6, 20);

INSERT INTO Komentar (username, idSetlist, komentar) VALUES
('user1', 1, 'Amazing performance!'), 
('user2', 2, 'Loved the songs!'), 
('user3', 3, 'Great setlist!'), 
('user4', 4, 'Nice energy on stage!'), 
('user5', 5, 'Good vibes!'), 
('user6', 6, 'Wonderful show!'), 
('user7', 7, 'Fantastic performance!'), 
('user8', 8, 'Enjoyed the concert!'),
('user9', 9, 'Would love to see again!'), 
('user10', 10, 'Great atmosphere!'),
('user11', 11, 'Beautiful music!'), 
('user12', 12, 'Incredible experience!'),
('user13', 13, 'Amazing band!'), 
('user14', 14, 'Fabulous concert!'),
('user15', 15, 'Great show!'), 
('user16', 16, 'Memorable event!'),
('user17', 17, 'Had a blast!'), 
('user18', 18, 'Such an amazing night!'),
('user19', 19, 'Unforgettable!'), 
('user20', 20, 'Awesome concert!');