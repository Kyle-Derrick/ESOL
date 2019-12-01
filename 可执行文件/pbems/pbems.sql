/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/11/21 22:47:20                          */
/*==============================================================*/


drop table if exists exm_question_lib;

drop table if exists exm_result;

drop table if exists exm_result_question;

drop table if exists exm_test_paper;

drop table if exists exm_test_question;

drop table if exists sys_dept;

drop table if exists sys_user;

/*==============================================================*/
/* Table: exm_question_lib                                      */
/*==============================================================*/
create table exm_question_lib
(
   question_lib_id      int(11) not null auto_increment comment '考题编号',
   question_kind        int default NULL comment '考题类型（1单选2多选3判断）',
   title                varchar(512),
   item_a               varchar(1024),
   item_b               varchar(1024),
   item_c               varchar(1024),
   item_d               varchar(1024),
   ref_answer           varchar(255),
   analysis             varchar(1024),
   vsrsion              int,
   primary key (question_lib_id)
);

/*==============================================================*/
/* Table: exm_result                                            */
/*==============================================================*/
create table exm_result
(
   result_id            int(11) not null auto_increment comment '考试结果编号',
   user_id              int(11) comment '用户编号',
   test_paper_id        int(11) comment '考试编号',
   duration             int default NULL comment '考试耗时',
   result_date          date default NULL comment '考试日期',
   sorce                int default NULL comment '考试成绩',
   sucess_num           int default NULL comment '正确率',
   error_num            int default NULL comment '错误题数',
   version              int,
   primary key (result_id)
);

/*==============================================================*/
/* Table: exm_result_question                                   */
/*==============================================================*/
create table exm_result_question
(
   result_question_id   int(11) not null auto_increment comment '考试答案编号',
   result_id            int(11) comment '考试结果编号',
   question_lib_id      int,
   question_kind        varchar(1024) comment '考题类型（1单选2多选3判断）',
   title                varchar(1024),
   item_a               varchar(1024),
   item_b               varchar(1024),
   item_c               varchar(1024),
   item_d               varchar(1024),
   ref_answer           varchar(255),
   analysis             varchar(1024),
   answer               varchar(64),
   version              int,
   primary key (result_question_id)
);

/*==============================================================*/
/* Table: exm_test_paper                                        */
/*==============================================================*/
create table exm_test_paper
(
   test_paper_id        int(11) not null auto_increment comment '考试编号',
   title                varchar(255),
   start_date           date default NULL comment '考试起始时间',
   end_date             date default NULL comment '考试截止时间',
   duration             int default NULL comment '考试时长',
   max_num              int default NULL comment '考试次数',
   sorce_rule           int default NULL comment '1最高分2平均分',
   total_sorce          int default NULL comment '总分',
   question_rule        int comment '1预先生成2考试随机',
   radio_num            int,
   mulitple_num         int,
   judge_num            int,
   total_num            int,
   version              int,
   primary key (test_paper_id)
);

/*==============================================================*/
/* Table: exm_test_question                                     */
/*==============================================================*/
create table exm_test_question
(
   test_question_id     int(11) not null auto_increment comment '试卷试题编号',
   question_lib_id      int(11) comment '考题编号',
   test_paper_id        int(11) comment '考试编号',
   sequence             int,
   version              int default NULL comment '试卷试题编号',
   primary key (test_question_id)
);

/*==============================================================*/
/* Table: sys_dept                                              */
/*==============================================================*/
create table sys_dept
(
   dept_id              int(11) not null auto_increment comment '部门编号',
   name                 varchar(64),
   leader               varchar(32) default NULL comment '部门负责人',
   info                 varchar(1024),
   parent_id            int default NULL comment '父级部门编号',
   full_id              varchar(1024),
   version              int,
   primary key (dept_id)
);

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   user_id              int(11) not null auto_increment comment '用户编号',
   dept_id              int(11) comment '部门编号',
   user_name            varchar(64),
   name                 varchar(32),
   telphone             varchar(64),
   user_kind            int comment '用户类型:1管理员2考试用户',
   version              int,
   primary key (user_id)
);

alter table exm_result add constraint FK_fk_test_paper_reuslt foreign key (test_paper_id)
      references exm_test_paper (test_paper_id) on delete restrict on update restrict;

alter table exm_result add constraint FK_fk_user_reslt foreign key (user_id)
      references sys_user (user_id) on delete restrict on update restrict;

alter table exm_result_question add constraint FK_fk_result_question foreign key (result_id)
      references exm_result (result_id) on delete restrict on update restrict;

alter table exm_test_question add constraint FK_fk_question_lib_question foreign key (question_lib_id)
      references exm_question_lib (question_lib_id) on delete restrict on update restrict;

alter table exm_test_question add constraint FK_fk_test_paper_question foreign key (test_paper_id)
      references exm_test_paper (test_paper_id) on delete restrict on update restrict;

alter table sys_user add constraint FK_fk_dept_user foreign key (dept_id)
      references sys_dept (dept_id) on delete restrict on update restrict;

