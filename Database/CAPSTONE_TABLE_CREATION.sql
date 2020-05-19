DROP TABLE IF EXISTS Transaction_Item;
DROP TABLE IF EXISTS Order_Item;
DROP TABLE IF EXISTS Comic;
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Transaction;
DROP TABLE IF EXISTS Refund;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Type;
DROP TABLE IF EXISTS Vendor;

CREATE TABLE Vendor(
	vendorID INT,
	vendor_name VARCHAR(30) NOT NULL,
	CONSTRAINT SYS_VENDORID_PK PRIMARY KEY(vendorID)
);

CREATE TABLE Type(
	item_type VARCHAR(10),
	CONSTRAINT SYS_ITEMTYPE_PK PRIMARY KEY(item_type)
);

CREATE TABLE Orders(
	order_no INT,
	orderDate DATE,
	arrivalDate DATE,
	VendorID INT,
	CONSTRAINT SYS_ORDERNO_PK PRIMARY KEY(order_no),
	CONSTRAINT SYS_VENDORID_FK1 FOREIGN KEY(vendorid) REFERENCES Vendor(vendorid)
);

CREATE TABLE Refund(
	refundID INT,
	transactionID INT,
	itemID INT,
	CONSTRAINT SYS_REFUNDID_PK PRIMARY KEY(refundID)
);

CREATE TABLE Transaction(
	transactionID INT,
	transaction_date DATETIME NOT NULL,
	final_price FLOAT (6,2) NOT NULL,
	CONSTRAINT SYS_TRANSACTIONID_PK PRIMARY KEY(transactionID)
);

CREATE TABLE Item(
	itemID INT,
	item_type VARCHAR(10) NOT NULL,
	-- vendorID INT NOT NULL, -- REMOVE LINE, ADD TO ORDERS
	description VARCHAR(50) NULL,
	price FLOAT(6,2) NOT NULL,
	name VARCHAR(50) NOT NULL,
	quantity INT,
	upc VARCHAR(17),
	CONSTRAINT SYS_ITEMID_PK PRIMARY KEY(itemID),
	CONSTRAINT SYS_ITEMTYPE_FK1 FOREIGN KEY(item_type) REFERENCES Type(item_type)
	-- CONSTRAINT SYS_VENDORTYPE_FK1 FOREIGN KEY(vendorID) REFERENCES Vendor(vendorID)
);

CREATE TABLE Comic(
	itemID INT,
	author VARCHAR(20) NOT NULL,
	volume INT NOT NULL,
	isbn VARCHAR(13) NOT NULL,
	publisher VARCHAR(15),
    CONSTRAINT SYS_COMICITEMID_PK PRIMARY KEY(itemID),
	CONSTRAINT SYS_COMICITEMID_FK FOREIGN KEY(itemID) REFERENCES Item(itemID)
);

CREATE TABLE Order_Item(
	itemID INT,
	order_no INT,
	purchase_price FLOAT(6,2) NOT NULL,
	orderQuantity INT,
    CONSTRAINT SYS_ORDERITEM_PK PRIMARY KEY(itemID, order_no),
	CONSTRAINT SYS_ORDERITEMID_FK FOREIGN KEY(itemID) REFERENCES Item(itemID),
	CONSTRAINT SYS_ORDERNO_FK FOREIGN KEY(order_no) REFERENCES Orders(order_no)
);

CREATE TABLE Transaction_Item(
	itemID INT NOT NULL,
	transactionID INT NOT NULL,
	quantity INT NOT NULL,
	sold_price FLOAT(6,2) NOT NULL,
    CONSTRAINT SYS_TRANSITEM_PK PRIMARY KEY(itemID, transactionID, quantity, sold_price),
	CONSTRAINT SYS_TRANSACTIONITEMID_FK FOREIGN KEY(itemID) REFERENCES Item(itemID),
	CONSTRAINT SYS_TRANSACTIONID_FK FOREIGN KEY(transactionID) REFERENCES Transaction(TransactionID)
);