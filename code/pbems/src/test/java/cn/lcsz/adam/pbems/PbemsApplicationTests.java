package cn.lcsz.adam.pbems;

import cn.lcsz.adam.pbems.exam.model.po.ExmResult;
import cn.lcsz.adam.pbems.exam.model.po.ExmResultQuestion;
import cn.lcsz.adam.pbems.exam.repository.ExmQuestionLibRepository;
import cn.lcsz.adam.pbems.exam.repository.ExmResultRepository;
import cn.lcsz.adam.pbems.exam.repository.ExmTestPaperRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PbemsApplicationTests {
@Autowired
    ExmResultRepository exmResultRepository;
@Autowired
    ExmTestPaperRepository exmTestPaperRepository;
    @Autowired
    ExmQuestionLibRepository exmQuestionLibRepository;
    @Test
    void contextLoads() {

    }

}
