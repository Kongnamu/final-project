package com.work.community.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.work.community.dto.ItemDTO;
import com.work.community.entity.Item;
import com.work.community.repository.ItemRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {
	
	private final ItemRepository itemRepository;

	public void save(@Valid ItemDTO itemDTO, MultipartFile iimage) throws Exception {
		// 1. 파일을 서버에 저장하고, 
		if(!iimage.isEmpty()) { // 전달된 파일이 있으면
		  //String filepath = System.getProperty("user.dir") + "/src/main/resources/static/upload/item/";
		  
		  UUID uuid = UUID.randomUUID();  // 무작위 아이디 생성(중복파일의 이름을 생성해줌)
		  
		  String filename = uuid + "_" + iimage.getOriginalFilename(); // 원본 파일
		  String filepath = "C:/3workfiles/item/" + filename;
		  
		  // File 클래스 객체 생성
		  File savedFile = new File(filepath); // upload 폴더에 저장
		  iimage.transferTo(savedFile); 
		
		  // 2. 파일 이름은 db에 저장
		  itemDTO.setIfilename(filename);
		  itemDTO.setIfilepath(filepath); // 파일 경로 설정함
		}
		// dto -> entity로 변환
		Item item = Item.toSaveEntity(itemDTO);
		// entity를 db에 저장
		itemRepository.save(item);
	}

	public List<ItemDTO> findAll() {
		List<Item> itemList = itemRepository.findAll();
		List<ItemDTO> itemDTOList = new ArrayList<>();
		for(Item item : itemList) {
			ItemDTO itemDTO = ItemDTO.toSaveDTO(item);
			itemDTOList.add(itemDTO);
		}
		return itemDTOList;
	}
	
}
