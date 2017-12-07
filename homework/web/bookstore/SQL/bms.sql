CREATE TABLE tag (
    tag VARCHAR2(5)    PRIMARY KEY
);

CREATE TABLE tag_main (
    tag_main    VARCHAR2(30)    PRIMARY KEY
);

CREATE TABLE tag_sub (
    tag_sub     VARCHAR2(30)    PRIMARY KEY,
    tag_main    VARCHAR2(30)    REFERENCES tag_main(tag_main) ON DELETE CASCADE
);

CREATE TABLE tag_sub2 (
    tag_sub     VARCHAR2(30)    REFERENCES  tag_sub(tag_sub) ON DELETE CASCADE,
    tag_sub2    VARCHAR2(30)    PRIMARY KEY
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
    rating  NUMBER(3)       DEFAULT 4 CHECK (rating < 5), --관리자 1
    e_key   VARCHAR2(10)    DEFAULT 0 --인증no rating4, 인증ok rating3
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
	order_state	NUMBER(3)	    DEFAULT 0 --결제 확인 중(0), 배송 준비 중(1), 배송 중(2), 배송 완료(3), 환불 목록(9)
);

-- 주문 번호가 Primary key가 아님
-- 한 번 주문에 다수의 도서가 있을 경우 개별 저장이므로 주문 번호를 PrimaryKey로 할 수 없음
--CREATE TABLE refund (
--	order_num	    VARCHAR2(11) NOT NULL,
--    refun_state     NUMBER(3)	 DEFAULT 0 --환불 요청 중(0), 환불 완료(1)
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

INSERT INTO tag_main VALUES ('소설');
INSERT INTO tag_main VALUES ('장르소설');
INSERT INTO tag_main VALUES ('경제경영');
INSERT INTO tag_main VALUES ('자기계발');
INSERT INTO tag_main VALUES ('시/에세이');
INSERT INTO tag_main VALUES ('인문');
INSERT INTO tag_main VALUES ('국어/외국어');
INSERT INTO tag_main VALUES ('정치/사회');
INSERT INTO tag_main VALUES ('역사/문화');
INSERT INTO tag_main VALUES ('자연과학/공학');
INSERT INTO tag_main VALUES ('컴퓨터/인터넷');
INSERT INTO tag_main VALUES ('건강/의학');
INSERT INTO tag_main VALUES ('가정/생활/요리');
INSERT INTO tag_main VALUES ('여행/취미');
INSERT INTO tag_main VALUES ('예술/대중문화');
INSERT INTO tag_main VALUES ('매거진');
INSERT INTO tag_main VALUES ('종교');
INSERT INTO tag_main VALUES ('교재/수험서');
INSERT INTO tag_main VALUES ('청소년교양');
INSERT INTO tag_main VALUES ('유아');
INSERT INTO tag_main VALUES ('아동');
INSERT INTO tag_main VALUES ('코믹스');
INSERT INTO tag_main VALUES ('외국도서');
INSERT INTO tag_main VALUES ('eBook');
INSERT INTO tag_main VALUES ('e-오디오북');

INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '신경 끄기의 기술', '마크 맨슨', '갤리온', '20171027', 15000, 1000, '001.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'best', '자기계발');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '나미야 잡화점의 기적', '히가시노 게이고', '현대문학', '20121219', 14800, 1000, '006.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'best', '소설');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '살인자의 기억법', '김영학', '문학동네', '20130725', 10000, 1000, '003.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'best', '소설');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '82년생 김지영', '조남주', '민음사', '20161014', 13000, 1000, '002.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'best', '소설');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '나는 나로 살기로 했다', '김수현', '마음의숲', '20161128', 13800, 1000, '014.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'best', '시/에세이');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '언어의 온도', '이기주', '말글터', '20160819', 13800, 1000, '015.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'best', '시/에세이');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '너의 췌장을 먹고 싶어', '스미노 요류','소미미디어', '20170401', 13800, 1000, '011.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'good', '소설');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '미움받을 용기', '기시미 이치로, 고가 후미타케', '인플루엔셜', '20141117', 14900, 1000, '009.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'good', '인문');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '자존감 수업', '윤홍균', '심플라이프', '20160901', 14000, 1000, '013.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'good', '자기계발');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '나, 있는 그대로 참 좋다', '조유미', '허밍버드', '20170922', 13800, 1000, '005.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'good', '시/에세이');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '말의 품격', '이기주', '황소북스', '20170529', 14500, 1000, '010.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'good', '인문');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '스위프트 프로그래밍: Swift4', '야곰', '한빛미디어', '20171001', 32000, 1000, '012.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'new', '컴퓨터/인터넷');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '자바+안드로이드를 다루는 기술', '정재곤', '길벗', '20141031', 40000, 1000, '004.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'new', '컴퓨터/인터넷');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), '최범균의 JSP 2.3 웹 프로그래밍: 기초부터 중급까지', '최범균', '가메', '20151116', 27000, 1000, '007.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'new', '컴퓨터/인터넷');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), 'Oracle 11g 프로그래밍', '성윤정', '북스홀릭', '20111122', 23000, 1000, '008.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'new', '컴퓨터/인터넷');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), 'HTML5+CSS3 웹 표준의 정석(2017)', '고경희', '이지스퍼블리싱', '20170101', 28000, 1000, '016.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'new', '컴퓨터/인터넷');
INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), 'What Is History?', 'Carr, Edward Hallett', 'Vintage Books USA', '19671001', 21500, 1000, '017.jpg');
INSERT INTO book_sub (no, tag, tag_main) VALUES (LPAD(book_sq.currval, 4, '0'), 'null', '외국도서');

INSERT INTO member (id, pwd, name, phone, email, address, rating) VALUES ('dbs', '123', 'dbs', '010-1111-1111', 'nava', 'dd', 3);
INSERT INTO member (id, pwd, name, phone, email, address, rating) VALUES ('admin', '1234', '관리자', '010-0000-0000', 'bms@gmail.com', '벚꽃로', 1);

INSERT INTO bespeak VALUES ('20170708001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0017', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20170715001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0017', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20170722001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0017', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20170701001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0017', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20170701001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0011', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20170701001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0008', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20170701001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0009', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20171201001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0001', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20171202001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0001', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20171206001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0001', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20171208001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0017', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20171215001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0017', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20171222001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0017', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20160701001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0017', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20160701002', 'dbs', '받는이', '010-1111-1111', '받는주소', '0011', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20160701003', 'dbs', '받는이', '010-1111-1111', '받는주소', '009', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20151225001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0001', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20151225001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0002', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20151225001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0011', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20151225001', 'dbs', '받는이', '010-1111-1111', '받는주소', '0017', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20151225002', 'dbs', '받는이', '010-1111-1111', '받는주소', '0008', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20151225003', 'dbs', '받는이', '010-1111-1111', '받는주소', '0001', '1', '10000', '전화주세요', '111', 2);
INSERT INTO bespeak VALUES ('20151225004', 'dbs', '받는이', '010-1111-1111', '받는주소', '0001', '1', '10000', '전화주세요', '111', 2);

COMMIT;

SELECT * FROM tab;
SELECT * FROM bespeak;
SELECT * FROM book;
SELECT * FROM cart;
SELECT * FROM book_sub;
SELECT * FROM member;
SELECT * FROM mem_level;

DROP TABLE shipping;
DROP TABLE bespeak;
DROP TABLE cart;
DROP TABLE tag_sub2;
DROP TABLE tag_sub;
DROP TABLE member;
DROP TABLE book_sub;
DROP TABLE book;
DROP TABLE tag_main;
DROP TABLE tag;
DROP SEQUENCE order_sq;
DROP SEQUENCE book_sq;

DELETE FROM MEMBER;
DELETE FROM bespeak;
commit;


--SEQUENCE 초기화
SELECT LAST_NUMBER FROM user_sequences WHERE SEQUENCE_NAME = 'RECENT_SQ';
ALTER SEQUENCE RECENT_SQ INCREMENT BY -4;
SELECT recent_sq.nextval FROM dual;
SELECT recent_sq.currval FROM dual;
ALTER SEQUENCE RECENT_SQ INCREMENT BY 1;


















-- 도서 정보 - 최신 정렬 후 15개 출력
SELECT *
FROM (SELECT no, title, author, publisher, pub_date, price, count, image, rownum as num
        FROM (SELECT * FROM book ORDER BY no DESC)) -- 정렬한 테이블을 사용하지 않으면 1~15, 사용하면 2~16(최신)
WHERE num > 0 AND num <= 15; -- WHERE문을 따로 해야 실행됨

-- 도서 부가 정보 - 최신순 정렬 후 15개 출력
SELECT * 
FROM (SELECT no, tag, tag_main, intro, list, pub_intro, review, add_date, rownum as num 
        FROM (SELECT * FROM book_sub ORDER BY no DESC))
WHERE num > 0 AND num <= 15;

SELECT * FROM book_sub bs, book b WHERE b.no = bs.no AND tag = 'best';

-- 국내도서 최신순 출력. 외국도서 제외
SELECT *
FROM (SELECT * 
      FROM (SELECT * FROM book ORDER BY no DESC) b join (SELECT * FROM book_sub ORDER BY no DESC) bs 
        on b.no = bs.no)
WHERE tag_main != '외국도서'; 

-- 조건문을 밖에 두면 결과가 하나 적게 나온다.
SELECT *
FROM (SELECT b.no, title, author, publisher, pub_date, price, count, image, tag, tag_main, intro, list, pub_intro, review, add_date, rownum as num
      FROM (SELECT * FROM book ORDER BY no DESC) b join (SELECT * FROM book_sub ORDER BY no DESC) bs 
        on b.no = bs.no)
WHERE tag_main != '외국도서' AND num >= 1 AND num <= 5;

-- 조건문을 안에 두어야 결과가 제대로 나온다.
SELECT *
FROM (SELECT b.no, title, author, publisher, pub_date, price, count, image, tag, tag_main, intro, list, pub_intro, review, add_date, rownum as num
      FROM (SELECT * FROM book ORDER BY no DESC) b join (SELECT * FROM book_sub ORDER BY no DESC) bs 
            on b.no = bs.no 
      WHERE tag_main != '외국도서')
WHERE num >= 10 AND num <= 16;

SELECT *
FROM (SELECT b.no, title, author, publisher, pub_date, price, count, image, tag, tag_main, intro, list, pub_intro, review, add_date, rownum as num
      FROM book_sub bs, book b 
      WHERE b.no = bs.no AND tag = 'best')
WHERE num >= 1 AND num <= 5;

SELECT * FROM book b, book_sub bs WHERE b.no = bs.no AND tag_main = '시/에세이';


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

INSERT INTO cart VALUES('dbs', '0018', 'ㅇㅂㅇ', 100 , 10, '017.jpg');
--------------------------------------------------------------------------------


SELECT TO_CHAR(sysdate, 'yyyyMMdd') || LPAD(order_sq.currval, 3, '0') FROM dual;
INSERT INTO bespeak (order_num, id, name, phone, address, no,  count, price, etc, account) VALUES (TO_CHAR(sysdate, 'yyyyMMdd') || LPAD(order_sq.nextval, 3, '0'), 'dbs', '이름', '010-1111-1111', '주소', 1, 11, 10000, '메시지', '99303039');


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

--연간
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
--연간 환불액
SELECT substr(order_num, 1, 4), SUM(price*count)
FROM bespeak
WHERE order_state IN(8, 9)
GROUP BY substr(order_num, 1, 4);
--주간 환불액
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

--일별 매출액
SELECT md, SUM(price * count)
FROM (SELECT substr(order_num, 1, 8) as md, price, count
FROM bespeak
WHERE TO_DATE(SUBSTR(order_num, 1, 8), 'yyyyMMdd') >= TO_CHAR(TRUNC(sysdate,'MM'),'YYYYMMdd')
AND TO_DATE(SUBSTR(order_num, 1, 8), 'yyyyMMdd') <= LAST_DAY(sysdate))
GROUP BY md;
--TO_CHAR(round(SYSDATE,'W'),'W')+DECODE(TO_CHAR(SYSDATE,'d'),1,1,0)

--일별 태그별 매출액
SELECT md, SUM(price * count), tag
FROM (SELECT substr(b.order_num, 1, 8) as md, b.price, b.count, bs.tag
        FROM bespeak b, book_sub bs
        WHERE b.no = bs.no
            AND TO_DATE(SUBSTR(b.order_num, 1, 8), 'yyyyMMdd') >= TO_CHAR(TRUNC(sysdate,'MM'),'YYYYMMdd')
            AND TO_DATE(SUBSTR(b.order_num, 1, 8), 'yyyyMMdd') <= LAST_DAY(sysdate))
GROUP BY md, tag
ORDER BY md, tag;
