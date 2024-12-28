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
INSERT INTO Artis (namaArtis, urlGambarArtis) VALUES
('Shawn Mendes', '/assets/topArtist/shawnMendes.png'), ('Selena Gomez', '/assets/topArtist/selena.png'), 
('Abel Makkonen', '/assets/topArtist/abel.png'), ('Halsey', '/assets/topArtist/halsey.png'), 
('Billie Eilish', '/assets/topArtist/billie.png'), ('Justin Bieber', '/assets/topArtist/justin.png'), 
('Ariana Grande', '/assets/topArtist/ariana.png'), ('Ed Sheeran', '/assets/topArtist/edsheeran.png'), 
('Dua Lipa', '/assets/topArtist/dualipa.png'), ('BTS', '/assets/topArtist/bts.png'),
('Calum Scott', '/assets/topArtist/calumscott.png'), ('Adele', '/assets/topArtist/adele.png'), 
('Alan Walker', '/assets/topArtist/alanwalker.jpg'), ('Lauv', '/assets/topArtist/lauv.jpeg'), 
('James Arthur', '/assets/topArtist/jamesarthur.png'), ('Bruno Mars', '/assets/topArtist/brunomars.jpeg'), 
('Troye Sivan', '/assets/topArtist/troyesivan.jpg'), ('Taylor Swift', '/assets/topArtist/taylorswift.png'),
('Sam Smith', '/assets/topArtist/samsmith.jpg'), ('Jamie Miller', '/assets/topArtist/jamie miller.jpeg');

-- Insert into Album
INSERT INTO Album (namaAlbum, release_date, idArtis, urlGambarAlbum) VALUES
('Album 1', '2022-01-01', 1, '/images/album1.jpg'), ('Album 2', '2022-02-01', 2, '/assets/topArtist/logo1.png'),
('Album 3', '2022-03-01', 3, '/images/album3.jpg'), ('Album 4', '2022-04-01', 4, '/assets/topArtist/logo1.png'),
('Album 5', '2022-05-01', 5, '/images/album5.jpg'), ('Album 6', '2022-06-01', 6, '/assets/topArtist/logo1.png'),
('Album 7', '2022-07-01', 7, '/images/album7.jpg'), ('Album 8', '2022-08-01', 8, '/assets/topArtist/logo1.png'),
('Album 9', '2022-09-01', 9, '/images/album9.jpg'), ('Album 10', '2022-10-01', 10, '/assets/topArtist/logo1.png'),
('Album 11', '2022-11-01', 11, '/images/album11.jpg'), ('Album 12', '2022-12-01', 12, '/assets/topArtist/logo1.png'),
('Album 13', '2023-01-01', 13, '/images/album13.jpg'), ('Album 14', '2023-02-01', 14, '/assets/topArtist/logo1.png'),
('Album 15', '2023-03-01', 15, '/images/album15.jpg'), ('Album 16', '2023-04-01', 16, '/assets/topArtist/logo1.png'),
('Album 17', '2023-05-01', 17, '/images/album17.jpg'), ('Album 18', '2023-06-01', 18, '/assets/topArtist/logo1.png'),
('Album 19', '2023-07-01', 19, '/images/album19.jpg'), ('Album 20', '2023-08-01', 20, '/assets/topArtist/logo1.png');

-- Insert into Lagu
INSERT INTO Lagu (idAlbum, namaLagu, duration, idArtis, urlGambarLagu) VALUES
(1, 'Song 1', 180, 1, '/images/song1.jpg'), (2, 'Song 2', 190, 2, '/assets/topArtist/logo1.png'),
(3, 'Song 3', 200, 3, '/images/song3.jpg'), (4, 'Song 4', 210, 4, '/assets/topArtist/logo1.png'),
(5, 'Song 5', 220, 5, '/images/song5.jpg'), (6, 'Song 6', 230, 6, '/assets/topArtist/logo1.png'),
(7, 'Song 7', 240, 7, '/images/song7.jpg'), (8, 'Song 8', 250, 8, '/assets/topArtist/logo1.png'),
(9, 'Song 9', 260, 9, '/images/song9.jpg'), (10, 'Song 10', 270, 10, '/assets/topArtist/logo1.png'),
(11, 'Song 11', 280, 11, '/images/song11.jpg'), (12, 'Song 12', 290, 12, '/assets/topArtist/logo1.png'),
(13, 'Song 13', 300, 13, '/images/song13.jpg'), (14, 'Song 14', 310, 14, '/assets/topArtist/logo1.png'),
(15, 'Song 15', 320, 15, '/images/song15.jpg'), (16, 'Song 16', 330, 16, '/assets/topArtist/logo1.png'),
(17, 'Song 17', 340, 17, '/images/song17.jpg'), (18, 'Song 18', 350, 18, '/assets/topArtist/logo1.png'),
(19, 'Song 19', 360, 19, '/images/song19.jpg'), (20, 'Song 20', 370, 20, '/assets/topArtist/logo1.png');

-- Insert into Setlist
INSERT INTO Setlist (namaSetlist, tanggal, urlBukti, idShow) VALUES
('Setlist 1', '2022-01-01 20:00:00', '/images/setlist1.jpg', 1), ('Setlist 2', '2022-02-01 20:00:00', '/assets/topArtist/logo1.png', 2),
('Setlist 3', '2022-03-01 20:00:00', '/images/setlist3.jpg', 3), ('Setlist 4', '2022-04-01 20:00:00', '/assets/topArtist/logo1.png', 4),
('Setlist 5', '2022-05-01 20:00:00', '/images/setlist5.jpg', 5), ('Setlist 6', '2022-06-01 20:00:00', '/assets/topArtist/logo1.png', 6),
('Setlist 7', '2022-07-01 20:00:00', '/images/setlist7.jpg', 7), ('Setlist 8', '2022-08-01 20:00:00', '/assets/topArtist/logo1.png', 8),
('Setlist 9', '2022-09-01 20:00:00', '/images/setlist9.jpg', 9), ('Setlist 10', '2022-10-01 20:00:00', '/assets/topArtist/logo1.png', 10),
('Setlist 11', '2022-11-01 20:00:00', '/images/setlist11.jpg', 11), ('Setlist 12', '2022-12-01 20:00:00', '/assets/topArtist/logo1.png', 12),
('Setlist 13', '2023-01-01 20:00:00', '/images/setlist13.jpg', 13), ('Setlist 14', '2023-02-01 20:00:00', '/assets/topArtist/logo1.png', 14),
('Setlist 15', '2023-03-01 20:00:00', '/images/setlist15.jpg', 15), ('Setlist 16', '2023-04-01 20:00:00', '/assets/topArtist/logo1.png', 16),
('Setlist 17', '2023-05-01 20:00:00', '/images/setlist17.jpg', 17), ('Setlist 18', '2023-06-01 20:00:00', '/assets/topArtist/logo1.png', 18),
('Setlist 19', '2023-07-01 20:00:00', '/images/setlist19.jpg', 19), ('Setlist 20', '2023-08-01 20:00:00', '/assets/topArtist/logo1.png', 20);

-- Insert into Setlist_lagu
INSERT INTO Setlist_lagu (idSetlist, idLagu, idArtis, trackNumber) VALUES
(1, 1, 1, 1), (2, 2, 2, 2), (3, 3, 3, 3), (4, 4, 4, 4),
(5, 5, 5, 5), (6, 6, 6, 6), (7, 7, 7, 7), (8, 8, 8, 8),
(9, 9, 9, 9), (10, 10, 10, 10), (11, 11, 11, 11), (12, 12, 12, 12),
(13, 13, 13, 13), (14, 14, 14, 14), (15, 15, 15, 15), (16, 16, 16, 16),
(17, 17, 17, 17), (18, 18, 18, 18), (19, 19, 19, 19), (20, 20, 20, 20);

-- Insert into Komentar
INSERT INTO Komentar (username, idSetlist, komentar) VALUES
('user1', 1, 'Amazing performance!'), ('user2', 2, 'Loved the songs!'), 
('user3', 3, 'Great setlist!'), ('user4', 4, 'Nice energy on stage!'), 
('user5', 5, 'Good vibes!'), ('user6', 6, 'Wonderful show!'), 
('user7', 7, 'Fantastic performance!'), ('user8', 8, 'Enjoyed the concert!'),
('user9', 9, 'Would love to see again!'), ('user10', 10, 'Great atmosphere!'),
('user11', 11, 'Beautiful music!'), ('user12', 12, 'Incredible experience!'),
('user13', 13, 'Amazing band!'), ('user14', 14, 'Fabulous concert!'),
('user15', 15, 'Great show!'), ('user16', 16, 'Memorable event!'),
('user17', 17, 'Had a blast!'), ('user18', 18, 'Such an amazing night!'),
('user19', 19, 'Unforgettable!'), ('user20', 20, 'Awesome concert!');