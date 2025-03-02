-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 09, 2024 at 04:48 PM
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
-- Database: `netbanking`
--

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `transactionId` int(11) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `transactionType` varchar(50) DEFAULT NULL,
  `timestamp` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`transactionId`, `userId`, `amount`, `transactionType`, `timestamp`) VALUES
(1, 1, 599.00, 'Debit', '2024-10-08 20:12:41'),
(2, 2, 599.00, 'Credit', '2024-10-08 20:12:41'),
(3, 1, 4500.00, 'Debit', '2024-10-08 20:20:16'),
(4, 2, 4500.00, 'Credit', '2024-10-08 20:20:16'),
(5, 1, 40.00, 'Debit', '2024-10-09 00:38:24'),
(6, 2, 40.00, 'Credit', '2024-10-09 00:38:24'),
(7, 1, 5000.00, 'Debit', '2024-10-09 13:33:12'),
(8, 2, 5000.00, 'Credit', '2024-10-09 13:33:12'),
(9, 1, 5000.00, 'Debit', '2024-10-09 13:33:17'),
(10, 2, 5000.00, 'Credit', '2024-10-09 13:33:17'),
(11, 1, 5000.00, 'Debit', '2024-10-09 13:33:45'),
(12, 2, 5000.00, 'Credit', '2024-10-09 13:33:45'),
(13, 2, 8000.00, 'Debit', '2024-10-09 13:35:06'),
(14, 1, 8000.00, 'Credit', '2024-10-09 13:35:06'),
(15, 2, 22000.00, 'Debit', '2024-10-09 13:36:16'),
(16, 1, 22000.00, 'Credit', '2024-10-09 13:36:16');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userId` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `accountNumber` int(11) DEFAULT NULL,
  `balance` decimal(10,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userId`, `username`, `password`, `name`, `accountNumber`, `balance`) VALUES
(1, 'kunal2111', '12345', 'Kunal Kumar', 456789, 19262.00),
(2, 'george@21', '12345', 'George', 567899, 170139.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transactionId`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `accountNumber` (`accountNumber`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `transactionId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
