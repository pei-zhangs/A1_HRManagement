DROP TABLE security CASCADE CONSTRAINTS PURGE;

CREATE TABLE security
    ( employee_id	NUMBER(6) PRIMARY KEY
    , sec_id		VARCHAR2(20) UNIQUE
	, sec_password	varchar2(10)
	,sec_status		char(1) CONSTRAINT sec_sec_status_ck CHECK(sec_status IN ('I', 'A')),
	CONSTRAINT sec_emp_fk FOREIGN KEY (employee_id) REFERENCES employees (employee_id)
	) ;

INSERT INTO security VALUES ( 201, 'servlet', 'servlet', 'I');
INSERT INTO security VALUES ( 202, 'java', 'java', 'I');
INSERT INTO security VALUES ( 203, 'hr', 'hr', 'A');

COMMIT;