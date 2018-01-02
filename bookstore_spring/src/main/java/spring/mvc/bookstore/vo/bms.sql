CREATE TABLE tag (
    tag VARCHAR2(5)    PRIMARY KEY
);

CREATE TABLE tag_main (
    tag_main    VARCHAR2(30)    PRIMARY KEY
);

CREATE TABLE tag_sub (
    tag_main    VARCHAR2(30)    REFERENCES tag_main(tag_main) ON DELETE CASCADE,
    tag_sub     VARCHAR2(40) --ALTER TABLE TAG_SUB MODIFY TAG_SUB VARCHAR2(40);
);

--CREATE TABLE tag_sub2 (
--    tag_sub     VARCHAR2(30)    REFERENCES  tag_sub(tag_sub) ON DELETE CASCADE,
--    tag_sub2    VARCHAR2(30)    PRIMARY KEY
--);

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
    tag         VARCHAR2(5)     REFERENCES tag(tag) ON DELETE CASCADE,
    tag_main    VARCHAR2(30)    NOT NULL REFERENCES tag_main(tag_main) ON DELETE CASCADE,
    intro       VARCHAR2(255),
    list        VARCHAR2(255),
    pub_intro   VARCHAR2(255),
    review      VARCHAR2(255),
    add_date    DATE            DEFAULT sysdate
);

CREATE TABLE member (
    id      VARCHAR2(15)    PRIMARY KEY,
    pwd     VARCHAR2(20)    NOT NULL,
    name    VARCHAR2(10)    NOT NULL,
    phone   VARCHAR2(13),
    email   VARCHAR2(30)    NOT NULL,
    address VARCHAR2(50)    NOT NULL,
    memo    VARCHAR2(255),
    rating  NUMBER(3)       DEFAULT 4 CHECK (rating < 5), --������ 1
    e_key   VARCHAR2(10)    DEFAULT 0 --����no rating4, ����ok rating3
--    CONSTRAINT member_email_uk UNIQUE(email)
);

CREATE TABLE cart (
    id      VARCHAR2(15)    NOT NULL REFERENCES member(id) ON DELETE CASCADE,
    no      VARCHAR2(4)     NOT NULL REFERENCES book(no) ON DELETE CASCADE,
    title	VARCHAR2(100)	NOT NULL,
	price	NUMBER(38)		NOT NULL CHECK(price >= 0),
	count	NUMBER(38)		NOT NULL CHECK(count > 0),
    image   VARCHAR2(50)
);

CREATE SEQUENCE order_sq
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 9999;
    
CREATE TABLE bespeak (
	order_num	VARCHAR2(11),
	id		    VARCHAR2(15)	NOT NULL REFERENCES member(id) ON DELETE CASCADE,
	name		VARCHAR2(15)	NOT NULL,
	phone		VARCHAR2(13),
	address		VARCHAR2(255)	NOT NULL,
	no		    VARCHAR2(4)	    NOT NULL REFERENCES book(no) ON DELETE CASCADE,
	count		NUMBER(38)	    NOT NULL,
	price		NUMBER(38)	    NOT NULL,
	etc		    VARCHAR2(255),
	account		NUMBER(38)	    NOT NULL,
	order_state	NUMBER(3)	    DEFAULT 0 --���� Ȯ�� ��(0), ��� �غ� ��(1), ��� ��(2), ��� �Ϸ�(3), ȯ�� ���(9)
);

-- �ֹ� ��ȣ�� Primary key�� �ƴ�
-- �� �� �ֹ��� �ټ��� ������ ���� ��� ���� �����̹Ƿ� �ֹ� ��ȣ�� PrimaryKey�� �� �� ����
--CREATE TABLE refund (
--	order_num	    VARCHAR2(11) NOT NULL,
--    refun_state     NUMBER(3)	 DEFAULT 0 --ȯ�� ��û ��(0), ȯ�� �Ϸ�(1)
--);
--DROP TABLE refund;

CREATE TABLE shipping (
	order_num   VARCHAR2(11)    NOT NULL,
    ship_num    VARCHAR2(14)    NOT NULL
); 

INSERT INTO tag VALUES ('best');
INSERT INTO tag VALUES ('new');
INSERT INTO tag VALUES ('good');
INSERT INTO tag VALUES ('null');

INSERT INTO tag_main VALUES ('�Ҽ�');
INSERT INTO tag_main VALUES ('�帣�Ҽ�');
INSERT INTO tag_main VALUES ('����/�濵');
INSERT INTO tag_main VALUES ('�ڱ���');
INSERT INTO tag_main VALUES ('��/������');
INSERT INTO tag_main VALUES ('�ι�');
INSERT INTO tag_main VALUES ('�ܱ���');
INSERT INTO tag_main VALUES ('��ġ/��ȸ');
INSERT INTO tag_main VALUES ('����/��ȭ');
INSERT INTO tag_main VALUES ('�ڿ�����/����');
INSERT INTO tag_main VALUES ('��ǻ��/���ͳ�');
INSERT INTO tag_main VALUES ('�ǰ�');
INSERT INTO tag_main VALUES ('����/����');
INSERT INTO tag_main VALUES ('�丮');
INSERT INTO tag_main VALUES ('���/�ǿ�/������');
INSERT INTO tag_main VALUES ('����');
INSERT INTO tag_main VALUES ('����/���߹�ȭ');
INSERT INTO tag_main VALUES ('����');
INSERT INTO tag_main VALUES ('����');
INSERT INTO tag_main VALUES ('���/���輭');
INSERT INTO tag_main VALUES ('�ܱ�����');
INSERT INTO tag_main VALUES ('û�ҳ�');
INSERT INTO tag_main VALUES ('����');
INSERT INTO tag_main VALUES ('�Ƶ�');
INSERT INTO tag_main VALUES ('��ȭ');
INSERT INTO tag_main VALUES ('eBook');
INSERT INTO tag_main VALUES ('e-�������');

INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '�Ű� ������ ���', '��ũ �ǽ�', '������', '20171027', 15000, 1000, '001.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'best', '�ڱ���');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '���̾� ��ȭ���� ����', '�����ó� ���̰�', '���빮��', '20121219', 14800, 1000, '006.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'best', '�Ҽ�');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '�������� ����', '�迵��', '���е���', '20130725', 10000, 1000, '003.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'best', '�Ҽ�');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '82��� ������', '������', '������', '20161014', 13000, 1000, '002.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'best', '�Ҽ�');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '���� ���� ���� �ߴ�', '�����', '�����ǽ�', '20161128', 13800, 1000, '014.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'best', '��/������');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '����� �µ�', '�̱���', '������', '20160819', 13800, 1000, '015.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'best', '��/������');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '���� ������ �԰� �;�', '���̳� ���','�ҹ̵̹��', '20170401', 13800, 1000, '011.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'good', '�Ҽ�');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '�̿���� ���', '��ù� ��ġ��, �� �Ĺ�Ÿ��', '���÷翣��', '20141117', 14900, 1000, '009.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'good', '�ι�');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '������ ����', '��ȫ��', '���ö�����', '20160901', 14000, 1000, '013.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'good', '�ڱ���');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '��, �ִ� �״�� �� ����', '������', '��ֹ���', '20170922', 13800, 1000, '005.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'good', '��/������');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '���� ǰ��', '�̱���', 'Ȳ�ҺϽ�', '20170529', 14500, 1000, '010.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'good', '�ι�');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '������Ʈ ���α׷���: Swift4', '�߰�', '�Ѻ��̵��', '20171001', 32000, 1000, '012.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'new', '��ǻ��/���ͳ�');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '�ڹ�+�ȵ���̵带 �ٷ�� ���', '�����', '���', '20141031', 40000, 1000, '004.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'new', '��ǻ��/���ͳ�');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '�ֹ����� JSP 2.3 �� ���α׷���: ���ʺ��� �߱ޱ���', '�ֹ���', '����', '20151116', 27000, 1000, '007.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'new', '��ǻ��/���ͳ�');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), 'Oracle 11g ���α׷���', '������', '�Ͻ�Ȧ��', '20111122', 23000, 1000, '008.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'new', '��ǻ��/���ͳ�');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), 'HTML5+CSS3 �� ǥ���� ����(2017)', '�����', '�������ۺ���', '20170101', 28000, 1000, '016.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'new', '��ǻ��/���ͳ�');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), 'What Is History?', 'Carr, Edward Hallett', 'Vintage Books USA', '19671001', 21500, 1000, '017.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'null', '�ܱ�����');

INSERT INTO member (id, pwd, name, phone, email, address, rating) VALUES ('dbs', '123', 'dbs', '010-1111-1111', 'nava', 'dd', 3);
INSERT INTO member (id, pwd, name, phone, email, address, rating) VALUES ('admin', '1234', '������', '010-0000-0000', 'bms@gmail.com', '���ɷ�', 1);

INSERT INTO bespeak VALUES ('20170708001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20170715001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20170722001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20170701001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20170701001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0011', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20170701001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0008', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20170701001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0009', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20171201001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0001', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20171202001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0001', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20171206001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0001', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20171208001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20171215001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20171222001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20160701001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20160701002', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0011', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20160701003', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0009', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20151225001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0001', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20151225001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0002', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20151225001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0011', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20151225001', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0017', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20151225002', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0008', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20151225003', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0001', '1', '10000', '��ȭ�ּ���', '111', 2);
INSERT INTO bespeak VALUES ('20151225004', 'dbs', '�޴���', '010-1111-1111', '�޴��ּ�', '0001', '1', '10000', '��ȭ�ּ���', '111', 2);

COMMIT;

SELECT * FROM tab;
SELECT * FROM tag;
SELECT * FROM bespeak;
SELECT * FROM book;
SELECT * FROM cart;
SELECT * FROM book_sub;
SELECT * FROM member;
SELECT * FROM mem_level;

DROP TABLE shipping;
DROP TABLE bespeak;
DROP TABLE cart;
--DROP TABLE tag_sub2;
DROP TABLE tag_sub;
DROP TABLE member;
DROP TABLE book_sub;
DROP TABLE book;
DROP TABLE tag_main;
DROP TABLE tag;
DROP SEQUENCE order_sq;
DROP SEQUENCE book_sq;

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