package com.my.test;

import com.my.pojo.Student;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {

    public SqlSession get_session() throws IOException {
        // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 然后根据 sqlSessionFactory 得到 session
        SqlSession session = sqlSessionFactory.openSession();
        return session;
    }

    @Test
    public void select_all() throws IOException {
        // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 然后根据 sqlSessionFactory 得到 session
        SqlSession session = sqlSessionFactory.openSession();
        // 最后通过 session 的 selectList() 方法调用 sql 语句 listStudent
        List<Student> listStudent = session.selectList("listStudent");
        for (Student student : listStudent) {
            System.out.println("ID:" + student.getId() + ",NAME:" + student.getName());
        }
    }

    @Test
    public void add_func() throws IOException {
        SqlSession session = get_session();
        // 增加学生
        Student student1 = new Student();
//        student1.setId(4);
        student1.setStudentID(5);
        student1.setName("新增加的学生2");
        session.insert("addStudent", student1);

        // 提交修改
        session.commit();
        // 关闭 session
        session.close();
    }

    @Test
    public void delete() throws IOException {
        SqlSession session = get_session();
        Student student2 = new Student();
        student2.setId(6);
        session.delete("deleteStudent", student2);
        // 提交修改
        session.commit();
        // 关闭 session
        session.close();
    }

    @Test
    public void select_stu() throws IOException {
        SqlSession session = get_session();
        // 获取学生
        Student student3 = session.selectOne("getStudent", 2);
        System.out.println(student3.toString());
    }

    @Test
    public void update() throws IOException {
        SqlSession session = get_session();
        Student student3 = session.selectOne("getStudent", 2);
        // 修改学生
        student3.setName("修改的学生");
        session.update("updateStudent", student3);
        // 提交修改
        session.commit();
        // 关闭 session
        session.close();
    }

    @Test
    public void select_vague() throws IOException {
        SqlSession session = get_session();
        // 模糊查询
        List<Student> students = session.selectList("findStudentByName", "三颗心脏");
        for (Student student : students) {
            System.out.println("ID:" + student.getId() + ",NAME:" + student.getName());
        }
    }
}
