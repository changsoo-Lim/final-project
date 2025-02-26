package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserrollVO implements Serializable{
	private String userId;
	private String roleCd;
}
