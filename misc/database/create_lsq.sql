CREATE DATABASE lsq;
USE lsq;

CREATE TABLE supplier_invoice (
    id INT AUTO_INCREMENT PRIMARY KEY,
    supplier_id VARCHAR(255),
    invoice_id VARCHAR(255),
    invoice_date DATE,
    invoice_amount DECIMAL(7,2),
    terms INT,
    payment_date DATE,
    payment_amount DECIMAL(7,2),
    state VARCHAR(255),
    UNIQUE KEY (supplier_id, invoice_id)
)

SELECT si.supplierId, count(si.*) as `count` FROM supplier_invoice si where si.supplierId = 'supplier_7' GROUP BY si.supplierId;
