package it.univaq.disim.mwt.apollo.business;

import java.util.List;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.answers.MultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;

public interface AnswerService {
	
	List<MultiAnswer> findAllMultiAnswers() throws BusinessException;
	List<MultiChoiceMatrixAnswer> findAllMultiChoiceMatrixAnswers() throws BusinessException;
	List<SingleAnswer> findAllSingleAnswers() throws BusinessException;
	List<SingleChoiceMatrixAnswer> findAllSingleChoiceMatrixAnswers() throws BusinessException;
	
	ResponseGrid<MultiAnswer> findAllMultiAnswersPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<MultiChoiceMatrixAnswer> findAllMultiChoiceMatrixAnswersPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<SingleAnswer> findAlSingleAnswersPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<SingleChoiceMatrixAnswer> findAllSingleChoiceMatrixAnswersPaginated(RequestGrid request) throws BusinessException;
	
	MultiAnswer findMultiAnswerById(String id) throws BusinessException;
	MultiChoiceMatrixAnswer findMultiChoiceMatrixAnswerById(String id) throws BusinessException;
	SingleAnswer findSingleAnswerById(String id) throws BusinessException;
	SingleChoiceMatrixAnswer findSingleChoiceMatrixAnswerById(String id) throws BusinessException;
	
	void createAnswer(MultiAnswer answer) throws BusinessException;
	void createAnswer(MultiChoiceMatrixAnswer answer) throws BusinessException;
	void createAnswer(SingleAnswer answer) throws BusinessException;
	void createAnswer(SingleChoiceMatrixAnswer answer) throws BusinessException;
	
	void updateAnswer(MultiAnswer answer) throws BusinessException;
	void updateAnswer(MultiChoiceMatrixAnswer answer) throws BusinessException;
	void updateAnswer(SingleAnswer answer) throws BusinessException;
	void updateAnswer(SingleChoiceMatrixAnswer answer) throws BusinessException;
	
	void deleteAnswer(MultiAnswer answer) throws BusinessException;
	void deleteAnswer(MultiChoiceMatrixAnswer answer) throws BusinessException;
	void deleteAnswer(SingleAnswer answer) throws BusinessException;
	void deleteAnswer(SingleChoiceMatrixAnswer answer) throws BusinessException;
}
