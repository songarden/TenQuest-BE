package com.kns.tenquest.service;

import com.kns.tenquest.DtoList;
import com.kns.tenquest.dto.AnswerDto;
import com.kns.tenquest.dto.ReplyerDto;
import com.kns.tenquest.entity.Answer;
import com.kns.tenquest.entity.TemplateDoc;
import com.kns.tenquest.repository.AnswerRepository;
import com.kns.tenquest.repository.ReplyerRepository;
import com.kns.tenquest.repository.TemplateDocRepository;
import com.kns.tenquest.requestBody.MultipleAnswerRequestBody;
import com.kns.tenquest.requestBody.SingleAnswerCreateRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.kns.tenquest.util.PrimaryKeyGenerator;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerService {
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    ReplyerRepository replyerRepository;
    @Autowired
    TemplateDocRepository templateDocRepository;
    @Autowired
    PrimaryKeyGenerator generator;

    /* Test Done [23/03/27] */public DtoList<Answer> getAllAnswers(){
        return new DtoList<>(answerRepository.findAll(Sort.by(Sort.Direction.DESC, "answerTime")));
    }

    /* Test Done [23/03/27] */
    public DtoList<AnswerDto> getAnswerByDocId(Long docId){
        // Sorting Answer by unique question on template(docid)

        DtoList<AnswerDto> answerDtoList = new DtoList(answerRepository.findAnswerByDocId(docId));
        return answerDtoList;
    }

    /* working... */
    public List<Answer> getAnswerListByReplyerId(int replyerId){

        List<Answer> answers = answerRepository.findAnswerByReplyerId(replyerId);

        return answers;
    }

    /* working... */
    public List<String> getReplyerNameListByTemplateId(String templateId){
//        List<Template> templateList = templateDocRepository.findAllByTemplateId(templateId);
        List<TemplateDoc> templateDocList = templateDocRepository.findAllByTemplateId(templateId);

        Long templateDocId = templateDocList.get(0).getTemplateDocId();
        // get answers
        List<Answer> answers = answerRepository.findAnswerByDocId(templateDocId);
        List<String> replyerNameList = new ArrayList<>();
        for (int i=0; i <answers.size(); i++){
            int replyerId = answers.get(i).getReplyerId();
            String replyerName = replyerRepository.findReplyerByReplyerId(replyerId).get().getReplyerName();
            replyerNameList.add(replyerName);
        }
        return replyerNameList;
    }

    public void createAnswer(MultipleAnswerRequestBody reqBody){

        int generatedReplyerId = generator.replyerId();
        boolean isPublic = false;
        if (reqBody.isPublic.equals("true")) isPublic = true;

        for (int i =0; i<reqBody.docIdList.size(); i++){
            var sReqBody = SingleAnswerCreateRequestBody.builder()
                    .docId(reqBody.docIdList.get(i))
                    .answerContent(reqBody.answerContentList.get(i))
                    .replyerName(reqBody.replyerName)
                    .isPublic(reqBody.isPublic)
                    .build();

            /* Create Replyer */
            ReplyerDto replyerDto = ReplyerDto.builder()
                    .replyerId(generatedReplyerId)
                    .replyerName(sReqBody.replyerName)
                    .build();

            /* Save Replyer to database(replyer_table) */
            replyerRepository.save(replyerDto.toEntity());

            /* Create Answer */
            AnswerDto answerDto = AnswerDto.builder()
                    .answerId(generator.UUID())
                    .answerTime(generator.localDateTime())
                    //.answerContent(reqBody.answerContent.replace("\n", "\\r\\n"))
                    .answerContent(sReqBody.answerContent)
                    .docId(sReqBody.docId)
                    .replyerId(generatedReplyerId)
                    .isPublic(isPublic)
                    .build();

            /* Save answer to database(answer_table) */
            answerRepository.save(answerDto.toEntity());

            this.createSingleAnswer(sReqBody);
        }


    }

        /* Test Done [23/03/27] */
    public AnswerDto createSingleAnswer(SingleAnswerCreateRequestBody reqBody) {
        /* check fk valid */
        if(templateDocRepository.findById(reqBody.docId).isEmpty()){
            System.out.println("Result!! " + templateDocRepository.findById(reqBody.docId));
            return new AnswerDto();
        }

        /* setting values */
        int generatedReplyerId = generator.replyerId();
        boolean isPublic = false;
        if (reqBody.isPublic.equals("true")) isPublic = true;


        /* Create Replyer */
        ReplyerDto replyerDto = ReplyerDto.builder()
                .replyerId(generatedReplyerId)
                .replyerName(reqBody.replyerName)
                .build();

        /* Save Replyer to database(replyer_table) */
        replyerRepository.save(replyerDto.toEntity());

        /* Create Answer */
        AnswerDto answerDto = AnswerDto.builder()
                .answerId(generator.UUID())
                .answerTime(generator.localDateTime())
                //.answerContent(reqBody.answerContent.replace("\n", "\\r\\n"))
                .answerContent(reqBody.answerContent)
                .docId(reqBody.docId)
                .replyerId(generatedReplyerId)
                .isPublic(isPublic)
                .build();

        /* Save answer to database(answer_table) */
        answerRepository.save(answerDto.toEntity());
        return answerDto;

    }

}
