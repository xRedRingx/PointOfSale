-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 19, 2023 at 09:21 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pos`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `cartid` int(11) NOT NULL,
  `INID` int(11) NOT NULL,
  `Product_Name` varchar(50) NOT NULL,
  `Bar_code` varchar(50) NOT NULL,
  `qty` varchar(20) NOT NULL,
  `Unit_Price` varchar(20) NOT NULL,
  `Total_Price` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`cartid`, `INID`, `Product_Name`, `Bar_code`, `qty`, `Unit_Price`, `Total_Price`) VALUES
(66, 2, 'Hitman', '5032921020329', '1', '350', '350.0'),
(67, 2, 'Hitman', '5032921020329', '1', '350', '350.0'),
(68, 2, 'Hitman', '5032921020329', '1', '350', '350.0'),
(69, 2, 'Hitman', '5032921020329', '2', '350', '700.0'),
(70, 2, 'Hitman', '5032921020329', '2', '350', '700.0'),
(71, 3, 'Hitman', '5032921020329', '15', '350', '5250.0'),
(72, 4, 'Hitman', '5032921020329', '20', '350', '7000.0'),
(73, 5, 'Hitman', '5032921020329', '2', '350', '700.0'),
(74, 6, 'Hitman', '5032921020329', '2', '350', '700.0'),
(75, 7, 'Goufrite', '456', '3', '20', '60.0'),
(76, 7, 'Hitman', '5032921020329', '1', '350', '350.0'),
(77, 8, 'Goufrite', '456', '2', '20', '40.0'),
(78, 8, 'Goufrite', '456', '2', '20', '40.0'),
(79, 9, 'Hitman', '5032921020329', '3', '350', '1050.0'),
(80, 10, 'Goufrite', '456', '2', '20', '40.0'),
(81, 10, 'Hitman', '5032921020329', '3', '350', '1050.0'),
(82, 11, 'Goufrite', '456', '2', '20', '40.0'),
(83, 12, 'Goufrite', '456', '1', '20', '20.0'),
(84, 13, 'Goufrite', '456', '2', '20', '40.0'),
(85, 14, 'Goufrite', '456', '2', '20', '40.0');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `cid` int(10) NOT NULL,
  `customer_name` varchar(50) NOT NULL,
  `customer_lname` varchar(50) NOT NULL,
  `customer_phone` varchar(50) NOT NULL,
  `customer_adress` varchar(50) NOT NULL,
  `customer_email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`cid`, `customer_name`, `customer_lname`, `customer_phone`, `customer_adress`, `customer_email`) VALUES
(1, 'vide', 'vide', 'vide', 'vide', 'vide');

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `eid` int(10) NOT NULL,
  `employee_name` varchar(50) NOT NULL,
  `employee_lname` varchar(50) NOT NULL,
  `employee_phone` varchar(50) NOT NULL,
  `employee_adress` varchar(50) NOT NULL,
  `employee_email` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`eid`, `employee_name`, `employee_lname`, `employee_phone`, `employee_adress`, `employee_email`, `username`) VALUES
(3, 'mahdi', 'liamani', '0560620843', '140 cite 170', 'mah@gmail.com', 'mahdi46dz');

-- --------------------------------------------------------

--
-- Table structure for table `extra`
--

CREATE TABLE `extra` (
  `exid` int(11) NOT NULL,
  `val` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `extra`
--

INSERT INTO `extra` (`exid`, `val`) VALUES
(1, '14');

-- --------------------------------------------------------

--
-- Table structure for table `grn`
--

CREATE TABLE `grn` (
  `gid` int(11) NOT NULL,
  `grn_no` varchar(50) NOT NULL,
  `sid` varchar(10) NOT NULL,
  `barcode` varchar(50) NOT NULL,
  `prod_name` varchar(50) NOT NULL,
  `qty` varchar(50) NOT NULL,
  `buy_price` varchar(50) NOT NULL,
  `sell_price` varchar(50) NOT NULL,
  `exp_date` varchar(50) NOT NULL,
  `sub_total` varchar(50) NOT NULL,
  `discount` varchar(50) NOT NULL,
  `net_total` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `grn`
--

INSERT INTO `grn` (`gid`, `grn_no`, `sid`, `barcode`, `prod_name`, `qty`, `buy_price`, `sell_price`, `exp_date`, `sub_total`, `discount`, `net_total`) VALUES
(5, '2', '1', '5032921020329', 'Hitman', '50', '250', '350', '2023-00-05', '12500.0', '250.0', '12250.0'),
(6, '9', '0', '456', 'Goufrite', '200.0', '12', '20', '2023-06-09', '2400.0', '0.0', '2400.0'),
(7, '9', '0', '456', 'Goufrite', '200.0', '12', '20', '2023-06-09', '3900.0', '0.0', '3900.0'),
(8, '9', '0', '5032921020329', 'Hitman', '6.0', '250', '350', '2025-06-25', '3900.0', '0.0', '3900.0'),
(9, '9', '0', '456', 'Goufrite', '200.0', '12', '20', '2023-11-09', '3900.0', '0.0', '3900.0'),
(10, '9', '0', '5032921020329', 'Hitman', '6.0', '250', '350', '2023-11-09', '3900.0', '0.0', '3900.0'),
(11, '10', '1', '456', 'Goufrite', '200.0', '12', '20', '2023-11-09', '2400.0', '0.0', '2400.0'),
(12, '15', '1', '456', 'Goufrite', '191.0', '12', '20', '2023-11-02', '2292.0', '573.0', '1719.0'),
(13, '15', '1', '5032921020329', 'Hitman', '0.0', '250', '350', '2023-11-02', '2292.0', '573.0', '1719.0');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `pid` int(10) NOT NULL,
  `product_name` varchar(50) NOT NULL,
  `bar_code` varchar(50) NOT NULL,
  `buy_price` varchar(50) NOT NULL,
  `sell_price` varchar(50) NOT NULL,
  `qty` varchar(50) NOT NULL,
  `sid` varchar(50) NOT NULL,
  `s_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`pid`, `product_name`, `bar_code`, `buy_price`, `sell_price`, `qty`, `sid`, `s_name`) VALUES
(1, 'Hitman', '5032921020329', '250', '350', '0.0', '1', 'Chaibi'),
(2, 'Goufrite', '456', '12', '20', '191.0', '1', 'Chaibi');

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `Saleid` int(10) NOT NULL,
  `INID` int(11) NOT NULL,
  `cid` int(11) NOT NULL,
  `Customer_Name` varchar(50) NOT NULL,
  `Total_Qty` varchar(10) NOT NULL,
  `Total_Bill` varchar(20) NOT NULL,
  `Status` varchar(20) NOT NULL,
  `Balance` varchar(20) NOT NULL,
  `username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`Saleid`, `INID`, `cid`, `Customer_Name`, `Total_Qty`, `Total_Bill`, `Status`, `Balance`, `username`) VALUES
(2, 9, 1, 'vide', '3.0', '1050.0', 'Paye', '0.0', 'mahdi46dz'),
(3, 10, 1, 'vide', '5.0', '1090.0', 'Paye', '10.0', 'mahdi46dz'),
(4, 12, 1, 'vide', '1.0', '20.0', 'Non paye', '-20.0', 'mahdi46dz'),
(5, 14, 1, 'vide', '2.0', '40.0', 'Paye partial', '-20.0', 'mahdi46dz');

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

CREATE TABLE `suppliers` (
  `sid` int(10) NOT NULL,
  `supplier_name` varchar(50) NOT NULL,
  `supplier_lname` varchar(50) NOT NULL,
  `supplier_phone` varchar(50) NOT NULL,
  `supplier_adress` varchar(50) NOT NULL,
  `supplier_email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `suppliers`
--

INSERT INTO `suppliers` (`sid`, `supplier_name`, `supplier_lname`, `supplier_phone`, `supplier_adress`, `supplier_email`) VALUES
(1, 'Oussama ', 'Chaibi', '065232054', 'EL Malah 65 Lgts', 'oussma02@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `uid` int(11) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `password` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`uid`, `Username`, `password`) VALUES
(10, 'mahdi46dz', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`cartid`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`cid`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`eid`);

--
-- Indexes for table `extra`
--
ALTER TABLE `extra`
  ADD PRIMARY KEY (`exid`);

--
-- Indexes for table `grn`
--
ALTER TABLE `grn`
  ADD PRIMARY KEY (`gid`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`pid`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`Saleid`);

--
-- Indexes for table `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`sid`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`uid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `cartid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=86;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `cid` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `eid` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `extra`
--
ALTER TABLE `extra`
  MODIFY `exid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `grn`
--
ALTER TABLE `grn`
  MODIFY `gid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `pid` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `Saleid` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `sid` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
