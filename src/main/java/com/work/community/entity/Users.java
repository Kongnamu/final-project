package com.work.community.entity;

import com.work.community.dto.UsersDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor //모든 필드를 매개변수를 가진 생성자
@NoArgsConstructor  //기본 생성자
@Builder
@ToString
@Setter
@Getter
@Table(name = "users")
@Entity
public class Users extends BaseEntity {
	
	@Id  // 필수 입력 - PK
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer uno;  // 회원번호
	
	@Column(nullable = false,unique = true)
	private String uid;  // 아이디
   
	@Column(nullable = false)
	private String upassword;  // 비밀번호
   
	@Column(nullable = false, length = 10)
	private String uname;  // 이름 
   
	@Column(nullable = false, unique = true,length = 30)
	private String unickname; // 닉네임
	
	@Column(nullable = false)
	private String ugender; //성별
   
	@Column(nullable = true)
	private String uaddress; // 주소
	
	@Column(nullable = true)
	private String detailuaddress; //상세주소
   
	@Column(nullable = false, length = 30)
	private String uphone; // 핸드폰 번호
   
	@Column(nullable = false)
	private String ubirth; // 생년월일
   
	// @Column
	// private String role;   // 권한
	@Enumerated(EnumType.STRING) private Role role;
    
	// dto -> entity
	public static Users toSaveEntity(UsersDTO usersDTO) {
		Users users = Users.builder()
				           .uid(usersDTO.getUid())
				           .upassword(usersDTO.getUpassword())
				           .uname(usersDTO.getUname())
				           .unickname(usersDTO.getUnickname())
				           .ugender(usersDTO.getUgender())
				           .uaddress(usersDTO.getUaddress())
				           .detailuaddress(usersDTO.getDetailuaddress())
				           .uphone(usersDTO.getUphone())
				           .ubirth(usersDTO.getUbirth())
				           .role(usersDTO.getRole())
				           .build();
		return users;      
   }
	//회원 정보 수정을 위해 형변환
	public static Users toSaveUpdate(UsersDTO usersDTO) {
		Users users = Users.builder()
				.uno(usersDTO.getUno())
				.uid(usersDTO.getUid())
				.upassword(usersDTO.getUpassword())
				.uname(usersDTO.getUname())
				.unickname(usersDTO.getUnickname())
				.ugender(usersDTO.getUgender())
				.uaddress(usersDTO.getUaddress())
				.detailuaddress(usersDTO.getDetailuaddress())
				.uphone(usersDTO.getUphone())
				.ubirth(usersDTO.getUbirth())
				.role(usersDTO.getRole())
				.build();
		
		return users;
	}
	
}