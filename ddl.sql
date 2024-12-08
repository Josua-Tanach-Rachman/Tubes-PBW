CREATE TYPE rolePengguna AS ENUM ('admin', 'pengguna');

CREATE TABLE Pengguna (
    idPengguna SERIAL PRIMARY KEY,
    username VARCHAR(255),
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

CREATE TABLE Artis (
    idArtis SERIAL PRIMARY KEY,
    namaArtis VARCHAR(255) NOT NULL
);

CREATE TABLE Album(
    idAlbum SERIAL PRIMARY KEY,
    namaAlbum VARCHAR(255) NOT NULL,
    release_date DATE
);

CREATE TABLE Album_Artis(
    idAlbum INT REFERENCES Album(idAlbum) ON DELETE CASCADE,
    idArtis INT REFERENCES Artis(idArtis) ON DELETE CASCADE,
    PRIMARY KEY (idAlbum, idArtis)
);

CREATE TABLE Lagu(
    idLagu SERIAL PRIMARY KEY,
    idAlbum INT REFERENCES Album(idAlbum) ON DELETE CASCADE,
    namaLagu VARCHAR(255) NOT NULL,
    duration INT
);

CREATE TABLE Lagu_Artis(
    idLagu INT REFERENCES Lagu(idLagu) ON DELETE CASCADE,
    idArtis INT REFERENCES Artis(idArtis) ON DELETE CASCADE,
    PRIMARY KEY (idLagu, idArtis)
);

CREATE TABLE Setlist(
    idSetlist SERIAL PRIMARY KEY,
    namaSetlist VARCHAR(255) NOT NULL,
    tanggal TIMESTAMP,
    idLokasi INT REFERENCES Lokasi(idLokasi),
    urlBukti VARCHAR(255)
);

CREATE TABLE Setlist_lagu(
    idSetlist INT REFERENCES Setlist(idSetlist) ON DELETE CASCADE,
    idLagu INT REFERENCES Lagu(idLagu) ON DELETE CASCADE,
	idArtis INT REFERENCES Artis(idArtis),
    trackNumber INT,
    PRIMARY KEY (idSetlist, idLagu)
);

CREATE TABLE Pengguna_setlist(
    idPengguna INT REFERENCES pengguna(idPengguna) ON DELETE CASCADE,
    idSetlist INT REFERENCES Setlist(idSetlist) ON DELETE CASCADE,
    PRIMARY KEY (idPengguna, idSetlist)
);

CREATE TABLE Komentar(
    idKomentar SERIAL PRIMARY KEY,
    idPengguna INT REFERENCES Pengguna(idPengguna),
    idSetlist INT REFERENCES Setlist(idSetlist),
    komentar TEXT NOT NULL,
    tanggal TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE News(
    idNews SERIAL PRIMARY KEY,
    headlineNews TEXT,
    isiNews TEXT,
    urlGambar VARCHAR(255)
);

CREATE TABLE History_Perubahan(
    idHistory SERIAL PRIMARY KEY,
    idPengguna INT REFERENCES Pengguna(idPengguna),
    idKomentar INT REFERENCES Komentar(idKomentar)
);

CREATE TABLE Images (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    imageData BYTEA NOT NULL
);
