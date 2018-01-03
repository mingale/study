CREATE TABLE tag_main (
    tag_main    VARCHAR2(3)    PRIMARY KEY
);

CREATE SEQUENCE book_sq
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 9999;

CREATE TABLE book (
	no		    VARCHAR2(4)		PRIMARY KEY,
	title	    VARCHAR2(100)	NOT NULL,
	author	    VARCHAR2(50)	NOT NULL,
    publisher   VARCHAR2(50)    NOT NULL,
    pub_date    DATE            NOT NULL,
	price	    NUMBER(38)		NOT NULL,
	count	    NUMBER(38)		NOT NULL,
    image       VARCHAR2(50),
    CONSTRAINT book_price_ck CHECK(price >= 0),
    CONSTRAINT book_count_ck CHECK(count >= 0)
);

CREATE TABLE book_sub (
    no          VARCHAR2(4)     NOT NULL REFERENCES book(no) ON DELETE CASCADE,
    tag         NUMBER(3)       DEFAULT 0,
    tag_main    VARCHAR2(3)    NOT NULL REFERENCES tag_main(tag_main) ON DELETE CASCADE,
    intro       VARCHAR2(255),
    list        VARCHAR2(255),
    pub_intro   VARCHAR2(255),
    review      VARCHAR2(255),
    add_date    DATE            DEFAULT sysdate,
    views       NUMBER(10)      DEFAULT 0
);

CREATE TABLE member (
    id      VARCHAR2(15)    PRIMARY KEY,
    pwd     VARCHAR2(20)    NOT NULL,
    name    VARCHAR2(10)    NOT NULL,
    phone   VARCHAR2(13),
    email   VARCHAR2(30)    NOT NULL UNIQUE,
    address VARCHAR2(2000)  NOT NULL,
    memo    VARCHAR2(255),
    rating  NUMBER(3)       DEFAULT 4 CHECK (rating < 5), --������ 1
    e_key   VARCHAR2(10)    DEFAULT 0 --����no rating4, ����ok rating3
);

CREATE TABLE cart (
    id          VARCHAR2(15)    NOT NULL REFERENCES member(id) ON DELETE CASCADE,
    no          VARCHAR2(4)     NOT NULL REFERENCES book(no) ON DELETE CASCADE,
    title	    VARCHAR2(100)	NOT NULL,
	cart_price	NUMBER(38)		NOT NULL CHECK(cart_price >= 0),
	cart_count	NUMBER(38)		NOT NULL CHECK(cart_count > 0),
    image       VARCHAR2(50)
);

CREATE SEQUENCE order_sq
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 9999;
    
CREATE TABLE bespeak (
	order_num	    VARCHAR2(11),
	id		        VARCHAR2(15)	NOT NULL REFERENCES member(id) ON DELETE CASCADE,
	name		    VARCHAR2(15)	NOT NULL,
	phone		    VARCHAR2(13),
	address		    VARCHAR2(255)	NOT NULL,
	no		        VARCHAR2(4)	    NOT NULL REFERENCES book(no) ON DELETE CASCADE,
	order_count		NUMBER(38)	    NOT NULL,
	order_price		NUMBER(38)	    NOT NULL,
	etc		        VARCHAR2(255),
	bank		    VARCHAR2(30)	NOT NULL,
	account		    NUMBER(38)	    NOT NULL,
	order_state	    NUMBER(3)	    DEFAULT 0 --���� Ȯ�� ��(0), ��� �غ� ��(1), ��� ��(2), ��� �Ϸ�(3), ȯ�� ���(9)
);

-- �ֹ� ��ȣ�� Primary key�� �ƴ�
-- �� �� �ֹ��� �ټ��� ������ ���� ��� ���� �����̹Ƿ� �ֹ� ��ȣ�� PrimaryKey�� �� �� ����
CREATE TABLE shipping (
	order_num   VARCHAR2(11)    NOT NULL,
    ship_num    VARCHAR2(14)    NOT NULL
); 

CREATE TABLE wishlist (
	id	VARCHAR2(15)	NOT NULL REFERENCES member(id) ON DELETE CASCADE,
    no  VARCHAR2(4)  REFERENCES book(no) ON DELETE CASCADE
);

CREATE SEQUENCE tag_main_sq 
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 99;
    
CREATE SEQUENCE notice_sq
	START WITH 1
	INCREMENT BY 1
	MAXVALUE 9999;

CREATE TABLE notice (
	idx			VARCHAR2(5)		PRIMARY KEY,
	title		VARCHAR2(50)	NOT NULL,
	content		VARCHAR2(255)	NOT NULL,
	id			VARCHAR2(15)	REFERENCES member(id),
	add_date	DATE			DEFAULT sysdate
	
);

INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);
INSERT INTO tag_main VALUES (tag_main_sq.nextval);

INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '�Ű� ������ ���', '��ũ �ǽ�', '������', '20171027', 15000, 1000, '001.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 0, '4');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '���̾� ��ȭ���� ����', '�����ó� ���̰�', '���빮��', '20121219', 14800, 1000, '006.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 0, '1');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '�������� ����', '�迵��', '���е���', '20130725', 10000, 1000, '003.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 0, '1');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '82��� ������', '������', '������', '20161014', 13000, 1000, '002.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 0, '1');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '���� ���� ���� �ߴ�', '�����', '�����ǽ�', '20161128', 13800, 1000, '014.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 0, '5');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '����� �µ�', '�̱���', '������', '20160819', 13800, 1000, '015.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 0, '5');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '���� ������ �԰� �;�', '���̳� ���','�ҹ̵̹��', '20170401', 13800, 1000, '011.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 1, '1');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '�̿���� ���', '��ù� ��ġ��, �� �Ĺ�Ÿ��', '���÷翣��', '20141117', 14900, 1000, '009.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 1, '6');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '������ ����', '��ȫ��', '���ö�����', '20160901', 14000, 1000, '013.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 1, '4');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '��, �ִ� �״�� �� ����', '������', '��ֹ���', '20170922', 13800, 1000, '005.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 1, '5');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '���� ǰ��', '�̱���', 'Ȳ�ҺϽ�', '20170529', 14500, 1000, '010.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 1, '6');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '������Ʈ ���α׷���: Swift4', '�߰�', '�Ѻ��̵��', '20171001', 32000, 1000, '012.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 0, '11');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '�ڹ�+�ȵ���̵带 �ٷ�� ���', '�����', '���', '20141031', 40000, 1000, '004.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 0, '11');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '�ֹ����� JSP 2.3 �� ���α׷���: ���ʺ��� �߱ޱ���', '�ֹ���', '����', '20151116', 27000, 1000, '007.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 0, '11');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), 'Oracle 11g ���α׷���', '������', '�Ͻ�Ȧ��', '20111122', 23000, 1000, '008.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 0, '11');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), 'HTML5+CSS3 �� ǥ���� ����(2017)', '�����', '�������ۺ���', '20170101', 28000, 1000, '016.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 0, '11');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), 'What Is History?', 'Carr, Edward Hallett', 'Vintage Books USA', '19671001', 21500, 1000, '017.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 0, '21');

INSERT INTO member (id, pwd, name, phone, email, address, rating) VALUES ('dbs', '123', 'dbs', '010-1111-1111', 'nava', 'dd', 3);
INSERT INTO member (id, pwd, name, phone, email, address, rating) VALUES ('admin', '1234', '������', '010-0000-0000', 'bms@gmail.com', '���ɷ�', 1);

INSERT INTO bespeak VALUES ('20170708001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '��������', '111', 2);
INSERT INTO bespeak VALUES ('20170715001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '��������', '111', 2);
INSERT INTO bespeak VALUES ('20170722001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '��������', '111', 2);
INSERT INTO bespeak VALUES ('20170701001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '��������', '111', 2);
INSERT INTO bespeak VALUES ('20170701001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0011', '1', '10000', '��ȭ�ּ���', '�츮����', '111', 2);
INSERT INTO bespeak VALUES ('20170701001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0008', '1', '10000', '��ȭ�ּ���', '�츮����', '111', 2);
INSERT INTO bespeak VALUES ('20170701001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0009', '1', '10000', '��ȭ�ּ���', '�츮����', '111', 2);
INSERT INTO bespeak VALUES ('20171201001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0001', '1', '10000', '��ȭ�ּ���', '�츮����', '111', 2);
INSERT INTO bespeak VALUES ('20171202001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0001', '1', '10000', '��ȭ�ּ���', '�츮����', '111', 2);
INSERT INTO bespeak VALUES ('20171206001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0001', '1', '10000', '��ȭ�ּ���', '�츮����', '111', 2);
INSERT INTO bespeak VALUES ('20171208001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '�ϳ�����', '111', 2);
INSERT INTO bespeak VALUES ('20171215001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '�ϳ�����', '111', 2);
INSERT INTO bespeak VALUES ('20171222001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '�ϳ�����', '111', 2);
INSERT INTO bespeak VALUES ('20160701001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '�ϳ�����', '111', 2);
INSERT INTO bespeak VALUES ('20160701002', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0011', '1', '10000', '��ȭ�ּ���', '�ϳ�����', '111', 2);
INSERT INTO bespeak VALUES ('20160701003', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0009', '1', '10000', '��ȭ�ּ���', '�ϳ�����', '111', 2);
INSERT INTO bespeak VALUES ('20151225001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0001', '1', '10000', '��ȭ�ּ���', '�ϳ�����', '111', 2);
INSERT INTO bespeak VALUES ('20151225001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0002', '1', '10000', '��ȭ�ּ���', '��������', '111', 2);
INSERT INTO bespeak VALUES ('20151225001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0011', '1', '10000', '��ȭ�ּ���', '��������', '111', 2);
INSERT INTO bespeak VALUES ('20151225001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '��������', '111', 2);
INSERT INTO bespeak VALUES ('20151225002', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0008', '1', '10000', '��ȭ�ּ���', '��������', '111', 2);
INSERT INTO bespeak VALUES ('20151225003', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0001', '1', '10000', '��ȭ�ּ���', '��������', '111', 2);
INSERT INTO bespeak VALUES ('20151225004', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0001', '1', '10000', '��ȭ�ּ���', '��������', '111', 2);

COMMIT;

SELECT * FROM tab;
SELECT * FROM tag;
SELECT * FROM bespeak;
SELECT * FROM book;
SELECT * FROM cart;
SELECT * FROM book_sub;
SELECT * FROM member;
SELECT * FROM mem_level;

DROP TABLE wishlist;
DROP TABLE shipping;
DROP TABLE bespeak;
DROP TABLE cart;
--DROP TABLE tag_sub2;
--DROP TABLE tag_sub;
--DROP TABLE member_history;
DROP TABLE member;
DROP TABLE book_sub;
DROP TABLE book;
DROP TABLE tag_main;
--DROP TABLE tag;
DROP SEQUENCE order_sq;
DROP SEQUENCE book_sq;
DROP SEQUENCE tag_main_sq;
--DROP SEQUENCE mem_his_sq;

DELETE FROM MEMBER;
DELETE FROM book;
DELETE FROM book_sub;
DELETE FROM bespeak;
commit;


--SEQUENCE �ʱ�ȭ
SELECT LAST_NUMBER FROM user_sequences WHERE SEQUENCE_NAME = 'RECENT_SQ';
ALTER SEQUENCE RECENT_SQ INCREMENT BY -4;
SELECT recent_sq.nextval FROM dual;
SELECT recent_sq.currval FROM dual;
ALTER SEQUENCE RECENT_SQ INCREMENT BY 1;


















-- ���� ���� - �ֽ� ���� �� 15�� ���
SELECT *
FROM (SELECT no, title, author, publisher, pub_date, price, count, image, rownum as num
        FROM (SELECT * FROM book ORDER BY no DESC)) -- ������ ���̺��� ������� ������ 1~15, ����ϸ� 2~16(�ֽ�)
WHERE num > 0 AND num <= 15; -- WHERE���� ���� �ؾ� �����

-- ���� �ΰ� ���� - �ֽż� ���� �� 15�� ���
SELECT * 
FROM (SELECT no, tag, tag_main, intro, list, pub_intro, review, add_date, rownum as num 
        FROM (SELECT * FROM book_sub ORDER BY no DESC))
WHERE num > 0 AND num <= 15;

SELECT * FROM book_sub bs, book b WHERE b.no = bs.no AND tag = 'best';

-- �������� �ֽż� ���. �ܱ����� ����
SELECT *
FROM (SELECT * 
      FROM (SELECT * FROM book ORDER BY no DESC) b join (SELECT * FROM book_sub ORDER BY no DESC) bs 
        on b.no = bs.no)
WHERE tag_main != '21'; 

-- ���ǹ��� �ۿ� �θ� ����� �ϳ� ���� ���´�.
SELECT *
FROM (SELECT b.no, title, author, publisher, pub_date, price, count, image, tag, tag_main, intro, list, pub_intro, review, add_date, rownum as num
      FROM (SELECT * FROM book ORDER BY no DESC) b join (SELECT * FROM book_sub ORDER BY no DESC) bs 
        on b.no = bs.no)
WHERE tag_main != '21' AND num >= 1 AND num <= 5;

-- ���ǹ��� �ȿ� �ξ�� ����� ����� ���´�.
SELECT *
FROM (SELECT b.no, title, author, publisher, pub_date, price, count, image, tag, tag_main, intro, list, pub_intro, review, add_date, rownum as num
      FROM (SELECT * FROM book ORDER BY no DESC) b join (SELECT * FROM book_sub ORDER BY no DESC) bs 
            on b.no = bs.no 
      WHERE tag_main != '21')
WHERE num >= 10 AND num <= 16;

SELECT *
FROM (SELECT b.no, title, author, publisher, pub_date, price, count, image, tag, tag_main, intro, list, pub_intro, review, add_date, rownum as num
      FROM book_sub bs, book b 
      WHERE b.no = bs.no AND tag = 'best')
WHERE num >= 1 AND num <= 5;

SELECT * FROM book b, book_sub bs WHERE b.no = bs.no AND tag_main = '5';


-----------------------------------------------------------------------------------
SELECT LPAD(LPAD(book_sq.nextval, 4, '0'), 4, '0') FROM dual;
--------------------------------------------------------------------------------

INSERT INTO mem_level (id) VALUES ('dbs3');
INSERT INTO mem_level (id, rating) VALUES ('admin', 1);

SELECT *
FROM (SELECT id, pwd, name, phone, email, address, rownum as num FROM member)
WHERE num > 1 AND num < 5;
-------------------------------------------------------------------------------

DELETE FROM cart;

SELECT *
FROM cart c, book b
WHERE c.no = b.no;

INSERT INTO cart VALUES('dbs', '0018', '������', 100 , 10, '017.jpg');
--------------------------------------------------------------------------------


SELECT TO_CHAR(sysdate, 'yyyyMMdd') || LPAD(order_sq.currval, 3, '0') FROM dual;
INSERT INTO bespeak (order_num, id, name, phone, address, no,  count, price, etc, account) VALUES (TO_CHAR(sysdate, 'yyyyMMdd') || LPAD(order_sq.nextval, 3, '0'), 'dbs', '�̸�', '010-1111-1111', '�ּ�', 1, 11, 10000, '�޽���', '99303039');


--------------------------------------------------------------------------------
SELECT * 
FROM (SELECT no, title, author, publisher, pub_date, price, count, image, tag, tag_main, intro, list, pub_intro, review, add_date, rownum as num
FROM (SELECT b.no, b.title, b.author, b.publisher, b.pub_date, b.price, b.count, b.image, bs.tag, bs.tag_main, bs.intro, bs.list, bs.pub_intro, bs.review, bs.add_date FROM book b, book_sub bs WHERE b.no = bs.no ORDER BY b.no DESC)
    ) 
WHERE num >= 1 AND num <= 5;


SELECT no, title, author, publisher, pub_date, price, count, image, tag, tag_main, intro, list, pub_intro, review, add_date, rownum as num
FROM (SELECT b.no, b.title, b.author, b.publisher, b.pub_date, b.price, b.count, b.image, bs.tag, bs.tag_main, bs.intro, bs.list, bs.pub_intro, bs.review, bs.add_date FROM book b, book_sub bs WHERE b.no = bs.no ORDER BY b.no DESC);

SELECT no, title FROM book;

SELECT * FROM bespeak ORDER BY order_num DESC;
SELECT * 
 FROM bespeak o, book b 
 WHERE o.no = b.no ;


    
SELECT * FROM bespeak;
SELECT * FROM (SELECT order_num, id, no, order_state, rownum FROM(SELECT * FROM bespeak WHERE order_state IN(8, 9))) WHERE rownum >= 1 AND rownum <=3 ;
SELECT COUNT(DISTINCT order_num) FROM bespeak WHERE order_state IN(8, 9);
SELECT SUM(count) FROM bespeak WHERE order_state NOT IN(0, 8, 9);

SELECT SUM(count) FROM bespeak b, book_sub bs WHERE b.no = bs.no AND tag = 
'good' AND order_state NOT IN(0, 8, 9);

--����
SELECT order_num 
FROM bespeak b, book_sub bs 
WHERE b.no = bs.no 
    AND tag = 'best' 
    AND order_state NOT IN(0, 8, 9)
    AND order_num LIKE '2017%';
    
SELECT * FROM bespeak WHERE order_state NOT IN(0, 8, 9);
SELECT substr(order_num, 1, 4) as year, SUM(count * PRICE) as price
FROM (SELECT * FROM bespeak WHERE order_state NOT IN(0, 8, 9))
GROUP BY substr(order_num, 1, 4);

SELECT substr(order_num, 1, 4) as year, SUM(count) as count
FROM (SELECT * FROM bespeak WHERE order_state NOT IN(0, 8, 9))
GROUP BY substr(order_num, 1, 4);

SELECT tag, sum(count) as count
FROM (SELECT * FROM bespeak b, book_sub bs WHERE b.no = bs.no AND order_state IN(8, 9))
GROUP BY tag, substr(order_num, 1, 4);

SELECT SUM(price * count) FROM bespeak WHERE order_state NOT IN(0, 8, 9);
SELECT tag, sum(count) as count FROM (SELECT * FROM bespeak b, book_sub bs WHERE b.no = bs.no AND order_state IN(8, 9)) GROUP BY tag, substr(order_num, 1, 4) HAVING substr(order_num, 1, 4) = TO_CHAR(sysdate, 'yyyy');

select * from bespeak WHERE order_state IN(8, 9);
--���� ȯ�Ҿ�
SELECT substr(order_num, 1, 4), SUM(price*count)
FROM bespeak
WHERE order_state IN(8, 9)
GROUP BY substr(order_num, 1, 4);
--�ְ� ȯ�Ҿ�
SELECT TO_CHAR(TO_DATE(md, 'yyyyMMdd'), 'IW')
FROM (SELECT substr(order_num, 1, 8) as md, SUM(price*count) as price
FROM bespeak
WHERE order_state IN(8, 9)
GROUP BY substr(order_num, 1, 8))
WHERE TO_date(md, 'yyyyMMdd') > TO_date(substr(md, 1,6)+01, 'yyyyMMdd');

SELECT TO_CHAR(round(TO_DATE(md, 'yyyyMMdd'), 'W'), 'W') as week
FROM (select substr(order_num, 1, 8) as md, SUM(price * count) as sales 
      from bespeak 
      GROUP BY substr(order_num, 1, 8)
);
SELECT TO_CHAR(TRUNC(SYSDATE,'MM'),'YYYYMMDD') FROM DUAL;

--�Ϻ� �����
SELECT md, SUM(price * count)
FROM (SELECT substr(order_num, 1, 8) as md, price, count
FROM bespeak
WHERE TO_DATE(SUBSTR(order_num, 1, 8), 'yyyyMMdd') >= TO_CHAR(TRUNC(sysdate,'MM'),'YYYYMMdd')
AND TO_DATE(SUBSTR(order_num, 1, 8), 'yyyyMMdd') <= LAST_DAY(sysdate))
GROUP BY md;
--TO_CHAR(round(SYSDATE,'W'),'W')+DECODE(TO_CHAR(SYSDATE,'d'),1,1,0)

--�Ϻ� �±׺� �����
SELECT md, SUM(price * count), tag
FROM (SELECT substr(b.order_num, 1, 8) as md, b.price, b.count, bs.tag
        FROM bespeak b, book_sub bs
        WHERE b.no = bs.no
            AND TO_DATE(SUBSTR(b.order_num, 1, 8), 'yyyyMMdd') >= TO_CHAR(TRUNC(sysdate,'MM'),'YYYYMMdd')
            AND TO_DATE(SUBSTR(b.order_num, 1, 8), 'yyyyMMdd') <= LAST_DAY(sysdate))
GROUP BY md, tag
ORDER BY md, tag;

