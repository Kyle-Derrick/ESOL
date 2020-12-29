package cn.kyle.esol;

import cn.kyle.esol.exam.repository.ExmQuestionLibRepository;
import cn.kyle.esol.exam.repository.ExmResultRepository;
import cn.kyle.esol.exam.repository.ExmTestPaperRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class EsolApplicationTests {
@Autowired
ExmResultRepository exmResultRepository;
@Autowired
ExmTestPaperRepository exmTestPaperRepository;
    @Autowired
    ExmQuestionLibRepository exmQuestionLibRepository;
    @Test
    void contextLoads() {
        System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
    }

}
