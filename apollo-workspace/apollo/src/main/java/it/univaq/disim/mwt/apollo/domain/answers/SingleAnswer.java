package it.univaq.disim.mwt.apollo.domain.answers;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Document(collection="Answers")
@TypeAlias("SingleAnswer")
public class SingleAnswer extends Answer {

	private String answer;
}
