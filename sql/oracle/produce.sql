-- 秒杀执行储存过程
-- 定义储存过程
-- 参数： in 参数   out输出参数
CREATE OR REPLACE PROCEDURE execute_seckill(v_seckill_id IN NUMBER ,
                                            v_phone IN NUMBER,
                                            v_kill_time IN DATE,
                                            r_result OUT NUMBER)
IS
  insert_count number DEFAULT 0;
  BEGIN

    insert into success_killed
    (seckill_id, user_phone, "CREATE_TIME",STATE)
    VALUES
      (v_seckill_id, v_phone, v_kill_time,1);
    UPDATE seckill
    SET "NUMBER" = "NUMBER" - 1
    WHERE seckill_id = v_seckill_id
          AND end_time > v_kill_time
          AND start_time < v_kill_time
          AND "NUMBER" > 0;
    COMMIT;
    r_result := 1;
    EXCEPTION
    WHEN OTHERS THEN
    ROLLBACK;
    r_result := -1;
    dbms_output.put_line('code:' || sqlcode);
    dbms_output.put_line('errm:' || sqlerrm);
  END;
