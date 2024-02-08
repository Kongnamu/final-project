package com.work.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.work.community.config.SecurityUser;
import com.work.community.dto.UsersDTO;
import com.work.community.entity.Role;
import com.work.community.entity.Users;
import com.work.community.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsersService {

	private final UsersRepository usersRepository;

	private final PasswordEncoder pwEncoder;

	public Users login(Users users) {
		// db에서 아이디 조회
		Optional<Users> findUsers = usersRepository.findByUid(users.getUid());
		if (findUsers.isPresent()) {
			return findUsers.get();
		} else {
			return null;
		}
	}

	// 회원 가입 처리
	public void save(UsersDTO usersDTO) {
		// 1. 비밀번호 암호화
		// 2. 권한 설정
		String encPW = pwEncoder.encode(usersDTO.getUpassword());
		usersDTO.setUpassword(encPW);
		usersDTO.setRole(Role.MEMBER);

		// dto -> entity 변환 메서드
		Users users = Users.toSaveEntity(usersDTO);
		usersRepository.save(users);
	}

	public List<UsersDTO> findAll() {
		// db에서 memberList를 가져옴
		List<Users> usersList = usersRepository.findAll();

		// 비어있는 memberDTOList를 생성
		List<UsersDTO> usersDTOList = new ArrayList<>();

		// memberDTOList에 memberDTO를 저장함
		for (Users users : usersList) {
			UsersDTO usersDTO = UsersDTO.toSaveDTO(users);
			usersDTOList.add(usersDTO);
		}
		return usersDTOList;
	}

	public UsersDTO findById(Integer uno) {
		// db에서 member 엔티티를 꺼내옴
		Optional<Users> findUsers = usersRepository.findById(uno);
		if (findUsers.isPresent()) { // 회원 정보가 있으면
			// entity -> dto 변환
			UsersDTO usersDTO = UsersDTO.toSaveDTO(findUsers.get());
			return usersDTO; // 정보를 가져와서 반환
		}
		return null;
	}

	public void deleteById(Integer uno) {
		usersRepository.deleteById(uno);
	}

	// 아이디 중복검사
	public String checkId(String uid) {
		Optional<Users> findUser = usersRepository.findByUid(uid);
		if (findUser.isEmpty()) {
			return "OK";
		} else {
			return "NO";
		}
	}

	// 닉네임 중복검사
	public String checkNickname(String unickname) {
		Optional<Users> checkNickname = usersRepository.findByUnickname(unickname);
		if (checkNickname.isEmpty()) {
			return "OK";
		}else {
			return "NO";
		}
	}
	//회원 수정페이지
	public UsersDTO findByUid(SecurityUser principal) {
		Optional<Users> user =  
				usersRepository.findByUid(principal.getUsername());
		UsersDTO usersDTO = UsersDTO.toSaveDTO(user.get());
		return usersDTO;
	}

	public void update(UsersDTO usersDTO) {
		String encPW = pwEncoder.encode(usersDTO.getUpassword());
		usersDTO.setUpassword(encPW);
		usersDTO.setRole(Role.MEMBER);
		
		Users users = Users.toSaveUpdate(usersDTO);
		usersRepository.save(users);
	}

}
