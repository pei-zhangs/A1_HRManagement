CREATE or replace PACKAGE p_security AS 
-- Declare externally visible
	TYPE cur_empinfo IS REF CURSOR;
-- Declare externally callable subprograms
	FUNCTION f_security( 
		p_secid IN security.sec_id%TYPE, 
		p_secpassword IN security.sec_password%TYPE) 
	RETURN NUMBER;
	
PROCEDURE p_emp_info (
	p_employeeid IN employees.employee_id%TYPE, 
	p_info OUT cur_empinfo);
END p_security;
/


create or replace PACKAGE BODY p_security
AS -- Fully define subprograms specified in package
  FUNCTION f_security(
      p_secid       IN security.sec_id%TYPE,
      p_secpassword IN security.sec_password%TYPE)
    RETURN NUMBER
  IS
    v_emp_id NUMBER;
    v_password security.sec_password%TYPE;
    v_status security.sec_status%TYPE;
  BEGIN
    -- Using LOWER() function to ensure the username in the login window is not case sensitive
    SELECT sec_password
    INTO v_password
    FROM security
    WHERE sec_id = LOWER(p_secid);
    SELECT sec_status INTO v_status FROM security WHERE sec_id = LOWER(p_secid);
    SELECT employee_id INTO v_emp_id FROM security WHERE sec_id = LOWER(p_secid);
    -- Return employee Id if the employee is HR and entered correct password, and the status is active
    IF UPPER(p_secid) = 'HR' AND p_secpassword = v_password AND v_status = 'A' THEN
      RETURN v_emp_id;
    ELSE
      RETURN 0;
    END IF;
  EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RETURN 0;
  END f_security;
  PROCEDURE p_emp_info(
      p_employeeid IN employees.employee_id%TYPE,
      p_info OUT cur_empinfo)
  IS
  BEGIN
    OPEN p_infO FOR SELECT * FROM employees WHERE employee_id = p_employeeid;
  END p_emp_info;
END;