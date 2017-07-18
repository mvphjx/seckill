-- 创建秒杀库存表
--保留字用 ""  转义

create table seckill
(
  seckill_id           NUMBER(15)           not null,
  name                  NVARCHAR2(100),
  "NUMBER"          NUMBER(15),
  start_time          DATE,
  end_time           DATE,
  create_time          DATE,
  constraint PK_seckill primary key (seckill_id)
);
alter table seckill modify create_time  DATE  default sysdate   not  null ;
-- 插入初始化数据


insert into seckill
(seckill_id, name, "NUMBER", start_time, end_time)
values
  (HIBERNATE_SEQUENCE.nextval,
   '1000元秒杀iphone6',
   100,
   sysdate,
   sysdate + 100);
insert into seckill
(seckill_id, name, "NUMBER", start_time, end_time)
values
  (HIBERNATE_SEQUENCE.nextval,
   '500元秒杀iPad air',
   200,
   sysdate - 10,
   sysdate + 100);
insert into seckill
(seckill_id, name, "NUMBER", start_time, end_time)
values
  (HIBERNATE_SEQUENCE.nextval,
   '300元秒杀小米6',
   300,
   sysdate - 1,
   sysdate + 100);
insert into seckill
(seckill_id, name, "NUMBER", start_time, end_time)
values
  (HIBERNATE_SEQUENCE.nextval,
   '200元秒杀红米note',
   400,
   sysdate - 100,
   sysdate + 100);
-- 秒杀成功明细表
-- 用户登录相关信息
create table success_killed(
  seckill_id number NOT NULL,
  user_phone number NOT NULL ,
  state number NOT NULL ,
  "CREATE_TIME" date ,
  PRIMARY KEY (seckill_id,user_phone) /*联合主键*/)











