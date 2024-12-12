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
    idLokasi INT REFERENCES Lokasi(idLokasi),
    urlGambarShow VARCHAR(255)
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
