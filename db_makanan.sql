-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Waktu pembuatan: 16 Nov 2018 pada 09.42
-- Versi server: 10.1.34-MariaDB
-- Versi PHP: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_makanan`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tblkategorimakanan`
--

CREATE TABLE `tblkategorimakanan` (
  `id_kategori` varchar(30) NOT NULL,
  `nama_kategori` varchar(38) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tblkategorimakanan`
--

INSERT INTO `tblkategorimakanan` (`id_kategori`, `nama_kategori`) VALUES
('1', 'martabak'),
('2', 'aneka nasi'),
('3', 'aneka ayam & bebek'),
('4', 'snack & jajanan'),
('5', 'pizza & pasta'),
('6', 'bakmie');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tblmakanan`
--

CREATE TABLE `tblmakanan` (
  `makanan` varchar(30) NOT NULL,
  `id_makanan` int(20) NOT NULL,
  `foto_makanan` varchar(300) NOT NULL,
  `id_user` int(11) NOT NULL,
  `insert_time` varchar(100) NOT NULL,
  `id_kategori` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tblmakanan`
--

INSERT INTO `tblmakanan` (`makanan`, `id_makanan`, `foto_makanan`, `id_user`, `insert_time`, `id_kategori`) VALUES
('ketoprak', 95, 'ketoprak.jpg', 109, '', '0'),
('ketoprak', 96, 'ketoprak.jpg', 0, '', '0'),
('asdsasa', 98, 'http://192.168.20.35/db_makanan/uploads/ketoprak.jpg', 54, '', '0'),
('ktp', 101, 'http://192.168.20.35/db_makanan/uploads/rendang.jpg', 50, '', '0'),
('ktp', 102, 'http://192.168.20.35/db_makanan/uploads/rendang.jpg', 50, '', '0'),
('mie ayam', 105, 'http://192.168.20.35/db_makanan/uploads/IMG-20170830-WA0001.jpg', 54, '', '0'),
('sdhfjdhfjshdj', 106, 'http://192.168.20.35/db_makanan/uploads/ketoprak.jpg', 55, '', '0'),
('asdsa', 121, 'ketoprak.jpg', 107, '', '0'),
('rendang', 122, 'ketoprak.jpg', 117, '2017_09_14_10_21_09', 'aneka ayam & bebek'),
('rendang', 123, 'rendang.jpg', 114, '2017.09.14 at 10:25:49', 'pizza & pasta'),
('dsdsd', 124, 'rendang.jpg', 114, '', 'pizza & pasta'),
('lllll', 126, 'Screenshot_2017-04-16-18-40-46.png', 114, '', 'pizza & pasta'),
('sdsd', 130, 'ketoprak.jpg', 112, '2017.10.10 at 17:14:52', '0'),
('ketoprak', 131, 'ketoprak.jpg', 114, '2017.10.11 at 11:31:19', 'bakmie'),
('adsaas', 132, 'pic2017_10_09_16_52_24.jpg', 116, '2017.10.11 at 11:33:46', ''),
('ketoprak', 133, 'ketoprak.jpg', 117, '2017.10.24 at 16:19:29', 'aneka nasi'),
('bubur ikan', 135, 'IMG-20180808-WA0000.jpg', 120, '2018.08.08 at 13:21:03', 'aneka ayam & bebek'),
('satu', 137, 'IMG-20180808-WA0002.jpg', 120, '2018.08.08 at 13:27:09', 'martabak'),
('bakmie goreng', 138, '', 124, '2018.10.11 at 11:31:19', 'bakmie'),
('', 140, '', 125, '', ''),
('ayam kfc', 141, 'IMG-20181002-WA0002.jpeg', 130, '2018.08.25 at 10:43:32', 'aneka ayam & bebek'),
('cccc', 144, '01_python.png', 130, '2018.09.04 at 16:54:43', 'martabak'),
('snshs', 146, '01_python.png', 125, '2018.09.04 at 17:11:59', 'martabak'),
('nasi bakar', 147, '01_python.png', 125, '2018.09.04 at 16:11:53', 'aneka nasi'),
('martabak goreng', 148, '7e02f8ffd958906da36fd0b199963c55.jpg', 0, '', ''),
('testing', 149, 'IMG-20181002-WA0004.jpg', 130, '2018.10.02 at 16:17:33', 'martabak'),
('martabak orin', 151, 'Screenshot_20181106-100407.png', 133, '2018.11.06 at 16:50:29', '1'),
('testaja', 152, 'football_PNG52751.png', 133, '676732', 'pizza & pasta'),
('buah', 153, 'Screenshot_20181106-100407.png', 133, '2018.11.06 at 16:57:23', 'snack & jajanan');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbluser`
--

CREATE TABLE `tbluser` (
  `nama` varchar(30) NOT NULL,
  `alamat` varchar(30) NOT NULL,
  `jenkel` varchar(30) NOT NULL,
  `no_telp` varchar(12) NOT NULL,
  `id_user` int(11) NOT NULL,
  `username` varchar(130) NOT NULL,
  `password` varchar(120) NOT NULL,
  `level` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tbluser`
--

INSERT INTO `tbluser` (`nama`, `alamat`, `jenkel`, `no_telp`, `id_user`, `username`, `password`, `level`) VALUES
('asdsad', 'sfdsf', 'Laki - Laki ', '023232', 107, 'sdsdsd', '96e79218965eb72c92a549dd5a330112', 'admin'),
('andi', 'padang', 'sds', '8222', 108, 'sandi', 'sandi11', 'admin'),
('andi', 'padang', 'laki-laki', '082311445674', 109, 'imastudio', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('kkkkk', 'jkjkj', 'Laki - Laki ', '818888', 110, 'kkkkk', 'e60bdb8ee95a621e87a74a5f5fb59990', 'admin'),
('kkkkk', 'kjkj', 'laki-laki', '88888866565', 111, 'kkkkkl', 'e60bdb8ee95a621e87a74a5f5fb59990', 'admin'),
('ima', 'slipi', 'laki-laki', '9898888', 112, 'imastudio1', 'e60bdb8ee95a621e87a74a5f5fb59990', 'admin'),
('lllllll', 'klklklklk', 'laki-laki', '89898989', 113, 'allllllll', '52c69e3a57331081823331c4e69d3f2e', 'admin'),
('aldlsadjl', 'jkhfkahskdh', 'laki-laki', '989898989', 114, 'username', 'e60bdb8ee95a621e87a74a5f5fb59990', 'admin'),
('data', 'data', 'laki-laki', '123654', 115, 'data', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('yusi', 'jlin', 'laki-laki', '0812', 116, 'yus', '4297f44b13955235245b2497399d7a93', 'admin'),
('andi', 'padang', 'laki-laki', '082311445674', 117, 'andi', 'fcea920f7412b5da7be0cf42b8c93759', 'user biasa'),
('zura', 'padang', 'laki-laki', '34343343', 118, 'zura', 'f9b4809d50c2c1b807ee4b57da1b0fb8', 'admin'),
('rizki', 'padang', 'laki-laki', '34343343', 119, 'rizki', 'f9b4809d50c2c1b807ee4b57da1b0fb8', 'admin'),
('saya', 'padang', 'laki-laki', '34343343', 120, 'saya@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('hhhhy', 'rffgy', 'laki -laki', '55555555', 121, 'cfggg', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('faisal', 'jakarta', 'laki-laki', '23i203232', 122, 'faisal', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('faisal', 'jakarta', 'laki-laki', '23i203232', 123, 'faisal2', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('anda', 'padang', 'laki - laki', '081545454', 124, 'anda@gmail.com', 'fcea920f7412b5da7be0cf42b8c93759', 'admin'),
('daba', 'padang', 'perempuan', '0821245424', 125, 'daba@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('Ferry', 'Alamat', 'laki - laki', '132456789012', 126, 'Ferry', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('fit', 'padang', 'laki-laki', '0238232', 127, 'fit', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('fit', 'padang', 'laki-laki', '0238232', 128, 'fits', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('saya', 'padang', 'laki-laki', '082343423423', 129, 'aku@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('fen', 'padang', 'perempuan', '0812433734', 130, 'fen@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('ara', 'jakarta', 'laki-laki', '082145454', 131, 'ara@gmail.co.', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('udin', 'slipi', 'laki-laki', '989289829', 132, 'udin', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('nia', 'jakarta', 'perempuan', '082828282', 133, 'nia@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('kita', 'padang', 'laki-laki', '082311144567', 134, 'kita@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tblkategorimakanan`
--
ALTER TABLE `tblkategorimakanan`
  ADD PRIMARY KEY (`id_kategori`,`nama_kategori`);

--
-- Indeks untuk tabel `tblmakanan`
--
ALTER TABLE `tblmakanan`
  ADD PRIMARY KEY (`id_makanan`,`id_kategori`);

--
-- Indeks untuk tabel `tbluser`
--
ALTER TABLE `tbluser`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tblmakanan`
--
ALTER TABLE `tblmakanan`
  MODIFY `id_makanan` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=154;

--
-- AUTO_INCREMENT untuk tabel `tbluser`
--
ALTER TABLE `tbluser`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=135;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
