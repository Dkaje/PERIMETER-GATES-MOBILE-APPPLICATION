-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 27, 2022 at 12:45 PM
-- Server version: 10.5.16-MariaDB
-- PHP Version: 7.3.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id19295085_perimeter`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `username` text COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `username`, `password`, `date`) VALUES
('PGBy-0892022', 'thomas', '2c56a0d95ef356be74303f619cb77c73', '2022-07-19 18:59:47');

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `reg` int(11) NOT NULL,
  `serial` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `cust_id` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `product` int(11) DEFAULT NULL,
  `category` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` float DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `image` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(20) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`reg`, `serial`, `cust_id`, `product`, `category`, `type`, `price`, `quantity`, `image`, `status`, `reg_date`) VALUES
(1, 'PGBy-097IRDK2022', 'PGBy-0902022', 1, 'Gates', 'Swing', 100000, 2, 'IMG1293098624.jpg', 'Ordered', '2022-07-19 21:38:29'),
(2, 'PGBy-0984FCT2022', 'PGBy-0902022', 1, 'Gates', 'Swing', 100000, 1, 'IMG1293098624.jpg', 'Ordered', '2022-07-19 22:07:55'),
(3, 'PGBy-1014JVD2022', 'PGBy-0992022', 4, 'Gates', 'Bi-Folding', 80000, 2, 'IMG1056699574.jpg', 'Ordered', '2022-07-20 05:38:16'),
(4, 'PGBy-1014JVD2022', 'PGBy-0992022', 7, 'Grills', 'Balcony Posts', 32000, 2, 'IMG375732187.jpg', 'Ordered', '2022-07-20 05:50:53'),
(5, 'PGBy-103R4TK2022', 'PGBy-1022022', 2, 'Gates', 'Vertical Lift', 120000, 4, 'IMG1203714454.jpg', 'Ordered', '2022-07-20 07:22:59'),
(6, 'PGBy-105PR5Y2022', 'PGBy-1022022', 12, 'Windows', 'Swing', 6000, 2, 'IMG258536642.jpg', 'Ordered', '2022-07-20 10:10:33'),
(7, 'PGBy-107P7MZ2022', 'PGBy-1062022', 5, 'Gates', 'Railed', 65000, 10, 'IMG1235083246.jpg', 'Ordered', '2022-07-20 11:17:44'),
(8, 'PGBy-1092HDN2022', 'PGBy-1082022', 4, 'Gates', 'Bi-Folding', 80000, 3, 'IMG1056699574.jpg', 'Ordered', '2022-07-25 11:55:01');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `cust_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `fname` text COLLATE utf8_unicode_ci NOT NULL,
  `lname` text COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contact` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `location` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT 0,
  `remarks` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`cust_id`, `fname`, `lname`, `username`, `email`, `contact`, `location`, `password`, `status`, `remarks`, `date`) VALUES
('PGBy-0902022', 'winnie', 'mutinda', 'winnie', 'winniemutinda@gmail.com', '254727 59 32 20', 'Nairobi', 'ec94e4c3a1fb9239678569faf661a1d1', 1, '', '2022-07-19 20:32:42'),
('PGBy-0992022', 'musyoki', 'Patrick', 'musyoki', 'musyoki@gmail.com', '254725 67 78 22', 'Nairobi', 'ec94e4c3a1fb9239678569faf661a1d1', 1, '', '2022-07-19 22:24:54'),
('PGBy-1022022', 'lilian', 'mukii', 'lilian', 'lilian@gmail.com', '254725 86 32 12', 'Meru', 'ec94e4c3a1fb9239678569faf661a1d1', 1, '', '2022-07-20 07:21:24'),
('PGBy-1062022', 'david', 'mutua', 'david', 'davidkilole@gmail.com', '254716 56 70 08', 'Meru', 'ec94e4c3a1fb9239678569faf661a1d1', 1, '', '2022-07-20 11:15:40'),
('PGBy-1082022', 'cosmas', 'kasimu', 'cosmas', 'cosmasmutua@gmail.com', '254726 59 32 21', 'Turkana', 'ec94e4c3a1fb9239678569faf661a1d1', 1, '', '2022-07-25 11:21:44');

-- --------------------------------------------------------

--
-- Table structure for table `disburse`
--

CREATE TABLE `disburse` (
  `id` int(11) NOT NULL,
  `ind` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mpesa` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `supplier` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `reg_date` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `disburse`
--

INSERT INTO `disburse` (`id`, `ind`, `mpesa`, `supplier`, `amount`, `reg_date`) VALUES
(1, '69AI2D58WK', 'QWE4TYR4AA', 'PGBy-0942022', 342875000, '2022-07-22 16:15:58');

-- --------------------------------------------------------

--
-- Table structure for table `driver`
--

CREATE TABLE `driver` (
  `driver_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `fname` text COLLATE utf8_unicode_ci NOT NULL,
  `lname` text COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contact` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT 0,
  `remarks` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `driver`
--

INSERT INTO `driver` (`driver_id`, `fname`, `lname`, `username`, `email`, `contact`, `password`, `status`, `remarks`, `date`) VALUES
('PGBy-0912022', 'Emmanuel', 'muoki', 'muoki', 'emmanuel@gmail.com', '254701 43 56 21', 'ec94e4c3a1fb9239678569faf661a1d1', 1, '', '2022-07-19 20:59:57');

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `id` int(11) NOT NULL,
  `customer` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rating` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `message` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reg_date` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reply` varchar(250) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `reply_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`id`, `customer`, `phone`, `name`, `rating`, `message`, `reg_date`, `reply`, `reply_date`) VALUES
(1, 'PGBy-0902022', '254727 59 32 20', 'winnie', NULL, 'the items where broken', '2022-07-19 22:20:04', 'Replied', '2022-07-19 22:21:25'),
(2, 'PGBy-1022022', '254725 86 32 12', 'lilian', NULL, 'broken goods', '2022-07-20 10:07:40', 'Replied', '2022-07-20 10:08:04'),
(3, 'PGBy-1062022', '254716 56 70 08', 'david', NULL, 'not delivered on time', '2022-07-20 11:26:06', 'Replied', '2022-07-20 11:26:35'),
(4, 'PGBy-0902022', '254727 59 32 20', 'winnie', NULL, 'hi', '2022-07-20 13:27:24', 'Pending', '2022-07-20 13:27:24'),
(5, 'PGBy-0902022', '254727 59 32 20', 'winnie', NULL, 'hi', '2022-07-20 13:27:25', 'Pending', '2022-07-20 13:27:25'),
(6, 'PGBy-1082022', '254726 59 32 21', 'cosmas', NULL, 'I am really disappointed by your service.The products where tempered and did not receive them as i was expecting.', '2022-07-25 14:16:37', 'Replied', '2022-07-25 14:17:49');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` bigint(20) NOT NULL,
  `mpesa` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `ship` float DEFAULT NULL,
  `cost` float DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `unit` float DEFAULT NULL,
  `cust_id` text COLLATE utf8_unicode_ci NOT NULL,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `location` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `category` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `material` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `coating` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dimension` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `set_date` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `finance` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `remarks` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `mode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` varchar(250) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Pending',
  `blacksmith` varchar(250) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `dispatcher` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `driver` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `drive` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `customer` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `mpesa`, `amount`, `ship`, `cost`, `quantity`, `unit`, `cust_id`, `name`, `phone`, `location`, `category`, `type`, `material`, `coating`, `dimension`, `set_date`, `finance`, `remarks`, `mode`, `image`, `blacksmith`, `dispatcher`, `driver`, `drive`, `customer`, `date`) VALUES
(1, 'QWE4SAW7WR', 300500, 500, 300000, 2, 150000, 'PGBy-0902022', 'winnie mutinda', '254727 59 32 20', 'Embu-marakwet-weta', 'Gates', 'Swing', 'Aluminium', 'Oil Based', 'Height: 15 ft By Length: 30 ft', 'Sunday, 31 July 2022', 'Approved', 'Thanks for Shopping with us', 'Shipped', '', 'Pending', 'Pending', NULL, 'Pending', 'Pending', '2022-07-19 20:46:49'),
(2, 'QRT5DER4AS', 300000, 0, 300000, 2, 150000, 'PGBy-0992022', 'musyoki Patrick', '254725 67 78 22', 'Direct', 'Gates', 'Swing', 'Iron', 'Oil Based', 'Height: 15 ft By Length: 30 ft', 'Saturday, 30 July 2022', 'Approved', 'Thanks for Shopping with us', 'Not Shipped', '', 'its ready for pickup', 'Pending', NULL, 'Pending', 'Pending', '2022-07-19 22:27:08'),
(3, 'QWE5DAE5TR', 50500, 500, 50000, 2, 25000, 'PGBy-0992022', 'musyoki Patrick', '254725 67 78 22', 'Kirinyaga-gatau-kirwa primary school ', 'Gates', 'Vertical Lift', 'Aluminium', 'Oil Based', 'Height: 8 ft By Length: 4 ft', 'Sunday, 31 July 2022', 'Approved', 'Thanks for Shopping with us', 'Shipped', '', 'done', 'Assigned', 'PGBy-0912022', 'Delivered', 'Delivered', '2022-07-19 22:46:24'),
(4, 'QWR4TRE5SE', 7000500, 500, 7000000, 100, 70000, 'PGBy-0992022', 'musyoki Patrick', '254725 67 78 22', 'Muranga-dru-dru shop', 'Gates', 'Swing', 'Iron', 'Oil Based', 'Height: 15 ft By Length: 10 ft', 'Saturday, 30 July 2022', 'Approved', 'Thanks for Shopping with us', 'Shipped', '', 'Pending', 'Pending', NULL, 'Pending', 'Pending', '2022-07-20 06:53:06'),
(5, 'QWE4SDR6SE', 140500, 500, 140000, 2, 70000, 'PGBy-1082022', 'cosmas kasimu', '254726 59 32 21', 'Embu-marakwel market-PCEA CHURCH', 'Gates', 'Vertical Lift', 'Iron', 'Oil Based', 'Height: 15 ft By Length: 10 ft', 'Thursday, 4 August 2022', 'Approved', 'Thanks for Shopping with us', 'Shipped', '', 'The product is ready for collection', 'Pending', NULL, 'Pending', 'Pending', '2022-07-25 13:47:29'),
(6, 'QWE5YUE6ER', 140500, 500, 140000, 2, 70000, 'PGBy-1082022', 'cosmas kasimu', '254726 59 32 21', 'Kirinyaga-karen town-kawauni', 'Gates', 'Vertical Lift', 'Aluminium', 'Oil Based', 'Height: 15 ft By Length: 10 ft', 'Wednesday, 17 August 2022', 'Approved', 'Thanks for Shopping with us', 'Shipped', '', 'Pending', 'Pending', NULL, 'Pending', 'Pending', '2022-07-27 07:54:39'),
(7, 'QWER4RTYT2', 410500, 500, 410000, 10, 41000, 'PGBy-1082022', 'cosmas kasimu', '254726 59 32 21', 'Mombasa-kisau-mji safi', 'Doors', 'Vertical Lift', 'Aluminium', 'Oil Based', 'Height: 12 ft By Length: 10 ft', 'Thursday, 11 August 2022', 'Approved', 'Thanks for Shopping with us', 'Shipped', '', 'its done it can be picked from next week', 'Assigned', 'PGBy-0912022', 'Delivered', 'Delivered', '2022-07-27 08:07:31');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `payid` int(11) NOT NULL,
  `serial` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `mpesa` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `orders` float DEFAULT NULL,
  `ship` float DEFAULT NULL,
  `cust_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `location` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` float DEFAULT 0,
  `driver` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `drive` varchar(20) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `customer` varchar(20) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `comment` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`payid`, `serial`, `mpesa`, `amount`, `orders`, `ship`, `cust_id`, `name`, `phone`, `mode`, `location`, `status`, `driver`, `drive`, `customer`, `comment`, `reg_date`) VALUES
(1, 'PGBy-097IRDK2022', 'QWE3TYR4DY', 200000, 200000, 0, 'PGBy-0902022', 'winnie mutinda', '254727 59 32 20', 'No Shipment', 'Direct', 1, 'Pending', 'Pending', 'Pending', 'Thanks for Shopping with us', '2022-07-19 21:40:55'),
(2, 'PGBy-0984FCT2022', 'QWE4DAS6DE', 100500, 100000, 500, 'PGBy-0902022', 'winnie mutinda', '254727 59 32 20', 'Shipped', 'Kirinyaga~~~rusee~~gakii market', 1, 'PGBy-0912022', 'Delivered', 'Rejected', 'Thanks for Shopping with us', '2022-07-19 22:09:26'),
(3, 'PGBy-1014JVD2022', 'QAR5FRE6SW', 224000, 224000, 0, 'PGBy-0992022', 'musyoki Patrick', '254725 67 78 22', 'No Shipment', 'Direct', 1, 'Pending', 'Pending', 'Pending', 'Thanks for Shopping with us', '2022-07-20 05:55:40'),
(4, 'PGBy-103R4TK2022', 'QWE4TRY5DD', 480500, 480000, 500, 'PGBy-1022022', 'lilian mukii', '254725 86 32 12', 'Shipped', 'Muranga~~~kemu~~kemumarket', 0, 'Pending', 'Pending', 'Pending', NULL, '2022-07-20 07:27:31'),
(5, 'PGBy-104Z85I2022', 'QWE4TRY5SW', 480500, 480000, 500, 'PGBy-1022022', 'lilian mukii', '254725 86 32 12', 'Shipped', 'Muranga~~~kemu~~kemumarket', 1, 'PGBy-0912022', 'Delivered', 'Accepted', 'Thanks for Shopping with us', '2022-07-20 07:28:38'),
(6, 'PGBy-105PR5Y2022', 'QWE2DRT5SW', 12500, 12000, 500, 'PGBy-1022022', 'lilian mukii', '254725 86 32 12', 'Shipped', 'Muranga~~~Kenyatta road~~knh', 0, 'Pending', 'Pending', 'Pending', NULL, '2022-07-20 10:11:28'),
(7, 'PGBy-107P7MZ2022', 'QWE1RTY2SA', 650500, 650000, 500, 'PGBy-1062022', 'david mutua', '254716 56 70 08', 'Shipped', 'Meru~~~makutano~~prison makutano', 1, 'PGBy-0912022', 'Delivered', 'Rejected', 'Thanks for Shopping with us', '2022-07-20 11:20:43'),
(8, 'PGBy-1092HDN2022', 'QWER4TYRE2', 240500, 240000, 500, 'PGBy-1082022', 'cosmas kasimu', '254726 59 32 21', 'Shipped', 'Muranga~~~drd~~emre', 1, 'PGBy-0912022', 'Delivered', 'Accepted', 'Thanks for Shopping with us', '2022-07-25 11:55:48');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `category` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` float DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `price` float DEFAULT NULL,
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `category`, `type`, `description`, `image`, `qty`, `quantity`, `price`, `reg_date`) VALUES
(1, 'Gates', 'Swing', 'wrought iron metal', 'IMG1293098624.jpg', 5, 2, 100000, '2022-07-19 21:33:26'),
(2, 'Gates', 'Vertical Lift', 'aluminium metal', 'IMG1203714454.jpg', 6, 2, 120000, '2022-07-19 21:34:16'),
(3, 'Gates', 'Vertical Pivot', 'cast iron', 'IMG1682704103.jpg', 5, 5, 75000, '2022-07-19 21:35:54'),
(4, 'Gates', 'Bi-Folding', 'wrought iron metal', 'IMG1056699574.jpg', 10, 5, 80000, '2022-07-20 04:44:09'),
(5, 'Gates', 'Railed', 'stainless steel', 'IMG1235083246.jpg', 12, 2, 65000, '2022-07-20 04:44:35'),
(6, 'Grills', 'Stair Grills', 'wrought iron metal', 'IMG1097482792.jpg', 5, 5, 35000, '2022-07-20 04:45:04'),
(7, 'Grills', 'Balcony Posts', 'wrought iron metal', 'IMG375732187.jpg', 5, 3, 32000, '2022-07-20 04:45:42'),
(8, 'Doors', 'Swing', 'wrought iron metal', 'IMG2008812349.jpg', 10, 10, 11000, '2022-07-20 04:47:45'),
(9, 'Doors', 'Vertical Lift', 'stainless steel', 'IMG106263138.jpg', 28, 28, 18000, '2022-07-20 04:48:47'),
(10, 'Doors', 'Vertical Pivot', 'aluminium', 'IMG1517383915.jpg', 5, 5, 20000, '2022-07-20 04:49:27'),
(11, 'Doors', 'Bi-Folding', 'aluminium', 'IMG1821171684.jpg', 6, 6, 12000, '2022-07-20 04:50:31'),
(12, 'Windows', 'Swing', 'aluminium', 'IMG258536642.jpg', 5, 3, 6000, '2022-07-20 04:51:28'),
(13, 'Windows', 'Vertical Lift', 'aluminium', 'IMG752648802.jpg', 18, 18, 6500, '2022-07-20 04:52:10'),
(14, 'Windows', 'Vertical Pivot', 'aluminium', 'IMG1626870606.jpg', 10, 10, 11000, '2022-07-20 04:52:59'),
(15, 'Windows', 'Bi-Folding', 'aluminium', 'IMG428736454.jpg', 12, 12, 8500, '2022-07-20 04:53:40'),
(16, 'Windows', 'Railed', 'stainless steel', 'IMG435789555.jpg', 15, 15, 4500, '2022-07-20 04:54:05'),
(17, 'Shoe Rack', 'Heavy Shoe Rack', 'wrought iron metal', 'IMG137774059.jpg', 14, 14, 3500, '2022-07-20 04:54:31'),
(18, 'Shoe Rack', 'Heavy Shoe Rack', 'wrought iron metal', 'IMG1252740868.jpg', 30, 30, 3600, '2022-07-20 04:54:56');

-- --------------------------------------------------------

--
-- Table structure for table `purchase`
--

CREATE TABLE `purchase` (
  `pur_id` int(11) NOT NULL,
  `category` text COLLATE utf8_unicode_ci NOT NULL,
  `type` text COLLATE utf8_unicode_ci NOT NULL,
  `quantity` float DEFAULT NULL,
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `purchase`
--

INSERT INTO `purchase` (`pur_id`, `category`, `type`, `quantity`, `reg_date`) VALUES
(1, 'Metal', 'Aluminium', 383, '2022-07-20 11:35:34'),
(2, 'Coating', 'Oil Based', 100, '2022-07-20 07:47:53'),
(3, 'Metal', 'Iron', 8000, '2022-07-20 07:50:51'),
(4, 'Metal', 'Steel', 2600, '2022-07-20 07:50:07');

-- --------------------------------------------------------

--
-- Table structure for table `reply`
--

CREATE TABLE `reply` (
  `id` int(11) NOT NULL,
  `customer` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reply` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reply_date` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `reply`
--

INSERT INTO `reply` (`id`, `customer`, `phone`, `name`, `reply`, `reply_date`) VALUES
(1, 'PGBy-0902022', '254727 59 32 20', 'winnie', 'we are sorry for that will check on that', '2022-07-19 22:21:25'),
(2, 'PGBy-0902022', '254727 59 32 20', 'winnie', 'we are sorry for that will check on that', '2022-07-19 22:21:27'),
(3, 'PGBy-1022022', '254725 86 32 12', 'lilian', 'we are sorry', '2022-07-20 10:08:04'),
(4, 'PGBy-1062022', '254716 56 70 08', 'david', 'we are sorry for inconvenience', '2022-07-20 11:26:35'),
(5, 'PGBy-1062022', '254716 56 70 08', 'david', 'we are sorry for inconvenience', '2022-07-20 11:26:36'),
(6, 'PGBy-1082022', '254726 59 32 21', 'cosmas', 'Apologies sir we shall ensure changes are done.', '2022-07-25 14:17:49');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staff_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `fname` text COLLATE utf8_unicode_ci NOT NULL,
  `lname` text COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contact` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `role` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT 0,
  `remarks` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staff_id`, `fname`, `lname`, `username`, `email`, `contact`, `role`, `password`, `status`, `remarks`, `date`) VALUES
('PGBy-0932022', 'snipher', 'marube', 'snipher', 'snipher@gmail.com', '254798 25 41 23', 'Finance', 'ec94e4c3a1fb9239678569faf661a1d1', 1, '', '2022-07-19 21:04:33'),
('PGBy-0952022', 'kelvin', 'kioko', 'kelvin', 'kelvin@gmail.com', '254720 17 69 22', 'Inventory', 'ec94e4c3a1fb9239678569faf661a1d1', 1, '', '2022-07-19 21:13:43'),
('PGBy-0962022', 'Martin', 'mbugua', 'Martin', 'martinmbugua@gmail.com', '254741 15 06 32', 'BlackSmith', 'ec94e4c3a1fb9239678569faf661a1d1', 1, '', '2022-07-19 21:26:25');

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `stock_id` int(11) NOT NULL,
  `pur_id` int(11) DEFAULT NULL,
  `category` text COLLATE utf8_unicode_ci NOT NULL,
  `type` text COLLATE utf8_unicode_ci NOT NULL,
  `quantity` float DEFAULT NULL,
  `price` float DEFAULT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `supplier` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(20) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `pay` varchar(20) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`stock_id`, `pur_id`, `category`, `type`, `quantity`, `price`, `description`, `image`, `supplier`, `status`, `pay`, `reg_date`) VALUES
(1, 4, 'Metal', 'Steel', 1100, 300000, '100 by 700 ft', 'IMG282730254.jpg', 'PGBy-0942022', 'Approved', 'Paid', '2022-07-22 16:15:58'),
(2, 3, 'Metal', 'Iron', 3000, 100000, '20 by 50 ft', 'IMG1105722646.jpg', 'PGBy-0942022', 'Pending', 'Pending', '2022-07-20 07:50:50'),
(3, 1, 'Metal', 'Aluminium', 125, 75000, '10 by 30 ft', 'IMG452481141.jpg', 'PGBy-0942022', 'Approved', 'Paid', '2022-07-22 16:15:58'),
(4, 1, 'Metal', 'Aluminium', 100, 35000, '45 by 60', 'IMG583831729.jpg', 'PGBy-0942022', 'Approved', 'Paid', '2022-07-22 16:15:58');

-- --------------------------------------------------------

--
-- Table structure for table `store`
--

CREATE TABLE `store` (
  `id` int(11) NOT NULL,
  `category` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `price` float DEFAULT NULL,
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `store`
--

INSERT INTO `store` (`id`, `category`, `type`, `image`, `quantity`, `price`, `reg_date`) VALUES
(1, 'Metal', 'Aluminium', 'IMG452481141.jpg', 0, 75000, '2022-07-20 09:18:27'),
(2, 'Metal', 'Steel', 'IMG282730254.jpg', 0, 300000, '2022-07-20 09:18:34'),
(3, 'Metal', 'Aluminium', 'IMG583831729.jpg', 0, 35000, '2022-07-20 11:36:42');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `supp_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `fname` text COLLATE utf8_unicode_ci NOT NULL,
  `lname` text COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contact` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT 0,
  `remarks` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`supp_id`, `fname`, `lname`, `username`, `email`, `contact`, `password`, `status`, `remarks`, `date`) VALUES
('PGBy-0922022', 'gladys', 'wanza', 'wanza', 'wanza@gmail.cpm', '254711 16 98 04', 'ec94e4c3a1fb9239678569faf661a1d1', 1, NULL, '2022-07-19 21:02:01'),
('PGBy-0942022', 'monica', 'wanza', 'monica', 'monicawanza@gmail.com', '254712 54 73 62', 'ec94e4c3a1fb9239678569faf661a1d1', 1, NULL, '2022-07-19 21:07:57'),
('PGBy-1002022', 'Sheldon', 'njagi', 'Sheldon', 'sheldon@gmail.com', '254707 67 05 33', 'ec94e4c3a1fb9239678569faf661a1d1', 1, NULL, '2022-07-19 22:58:56');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`reg`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`cust_id`);

--
-- Indexes for table `disburse`
--
ALTER TABLE `disburse`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `driver`
--
ALTER TABLE `driver`
  ADD PRIMARY KEY (`driver_id`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`payid`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `purchase`
--
ALTER TABLE `purchase`
  ADD PRIMARY KEY (`pur_id`);

--
-- Indexes for table `reply`
--
ALTER TABLE `reply`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staff_id`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`stock_id`);

--
-- Indexes for table `store`
--
ALTER TABLE `store`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`supp_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `reg` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `disburse`
--
ALTER TABLE `disburse`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `payid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `purchase`
--
ALTER TABLE `purchase`
  MODIFY `pur_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `reply`
--
ALTER TABLE `reply`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `stock`
--
ALTER TABLE `stock`
  MODIFY `stock_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `store`
--
ALTER TABLE `store`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
