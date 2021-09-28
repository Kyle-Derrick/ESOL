package cn.kyle.esol;

import cn.kyle.esol.base.util.BaseUtils;
import cn.kyle.esol.repository.exam.model.po.ExmTestPaper;
import cn.kyle.esol.repository.exam.model.po.ManageExamPaper;
import cn.kyle.esol.repository.exam.repository.ExmQuestionLibRepository;
import cn.kyle.esol.repository.exam.repository.ExmResultRepository;
import cn.kyle.esol.repository.exam.repository.ExmTestPaperRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.lang.reflect.InvocationTargetException;

@SpringBootTest
class EsolApplicationTests {
@Autowired
ExmResultRepository exmResultRepository;
@Autowired
ExmTestPaperRepository exmTestPaperRepository;
    @Autowired
    ExmQuestionLibRepository exmQuestionLibRepository;
    @Test
    void contextLoads() throws InvocationTargetException, IllegalAccessException {
        ManageExamPaper manageExamPaper = new ManageExamPaper();
        manageExamPaper.setMaxNum(12);
        ExmTestPaper exmTestPaper = new ExmTestPaper();
        exmTestPaper.setTestPaperId(1)
                .setMaxNum(1)
                .setTitle("12");
        System.out.println(exmTestPaper);
        BaseUtils.copyProperties(manageExamPaper, exmTestPaper);
        System.out.println(exmTestPaper);
        System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
    }

}
