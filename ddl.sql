CREATE TYPE rolePengguna AS ENUM ('admin', 'pengguna');

CREATE TABLE Pengguna (
    idPengguna SERIAL PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    nama VARCHAR(255) NOT NULL,
    role rolePengguna NOT NULL DEFAULT 'pengguna'
);

CREATE TABLE Lokasi(
    idLokasi SERIAL PRIMARY KEY,
    namaLokasi VARCHAR(255) NOT NULL
);

CREATE TABLE Artis (
    idArtis SERIAL PRIMARY KEY,
    namaArtis VARCHAR(255) NOT NULL
);

CREATE TABLE Albums(
    idAlbum SERIAL PRIMARY KEY,
    namaAlbum VARCHAR(255) NOT NULL,
    idArtis INT REFERENCES Artis(idArtis) ON DELETE CASCADE,
    release_date DATE
);

CREATE TABLE Lagu(
    idLagu SERIAL PRIMARY KEY,
    idAlbum INT REFERENCES Albums(idAlbum),
    idArtis INT REFERENCES Artis(idArtis),
    namaLagu VARCHAR(255) NOT NULL,
    duration INT
);

CREATE TABLE Setlists(
    idSetlist SERIAL PRIMARY KEY,
    namaSetlist VARCHAR(255) NOT NULL,
    idArtis INT REFERENCES Artis(idArtis),
    tanggal TIMESTAMP,
    description TEXT,
    idLokasi INT REFERENCES Lokasi(idLokasi),
    urlBukti VARCHAR(255)
);

CREATE TABLE Setlist_lagu(
    idSetlist INT REFERENCES Setlists(idSetlist) ON DELETE CASCADE,
    idLagu INT REFERENCES Lagu(idLagu) ON DELETE CASCADE,
    trackNumber INT,
    PRIMARY KEY (idSetlist, idLagu)
);

CREATE TABLE Pengguna_setlist(
    idPengguna INT REFERENCES pengguna(idPengguna) ON DELETE CASCADE,
    idSetlist INT REFERENCES Setlists(idSetlist) ON DELETE CASCADE,
    PRIMARY KEY (idPengguna, idSetlist)
);

CREATE TABLE Komentar(
    idKomentar SERIAL PRIMARY KEY,
    idPengguna INT REFERENCES Pengguna(idPengguna),
    idSetlist INT REFERENCES Setlists(idSetlist),
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
)