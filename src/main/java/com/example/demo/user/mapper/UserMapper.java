package com.example.demo.user.mapper;

import com.example.demo.auth.dto.JoinRequestDto;
import com.example.demo.auth.dto.JoinResponseDto;
import com.example.demo.user.entity.UserEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * MapStruct를 사용한 DTO ↔ Entity 변환 매퍼
 *
 * @Mapper 어노테이션 옵션:
 * - componentModel = "spring": Spring Bean으로 등록하여 의존성 주입 가능
 * - 컴파일 타임에 구현체가 자동 생성됨 (MapperImpl 클래스)
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * 정적 인스턴스 (Spring 없이 사용할 때를 위한 대안)
     * Spring 환경에서는 @Autowired로 주입받아 사용하는 것을 권장
     */
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * 회원가입 DTO를 User Entity로 변환 (비밀번호 암호화 포함)
     *
     * @param joinRequestDto 회원가입 요청 DTO
     * @param passwordEncoder 비밀번호 암호화를 위한 PasswordEncoder
     * @return 변환된 UserEntity (비밀번호가 암호화된 상태)
     *
     * 매핑 규칙:
     * - source: DTO의 필드명
     * - target: Entity의 필드명
     * - constant: 고정값 설정
     * - ignore: 매핑에서 제외 (null로 설정됨)
     * - expression: Java 표현식으로 복잡한 변환 로직 수행
     */
    @Mapping(source = "userId", target = "loginId")                                              // DTO의 userId → Entity의 loginId로 매핑
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(registerRequestDto.getPassword()))")  // 비밀번호 암호화
    @Mapping(target = "roleType", constant = "CUSTOMER")                                         // 회원가입 시 기본 역할을 CUSTOMER로 설정
    @Mapping(target = "accountNonLocked", constant = "true")                                     // 계정 잠금 상태를 false(잠금되지 않음)로 설정
    @Mapping(target = "deleted", constant = "false")                                            // 삭제 상태를 false로 설정
    @Mapping(target = "id", ignore = true)                                                      // DB에서 auto_increment로 생성되므로 무시
    @Mapping(target = "createdAt", ignore = true)                                               // DB에서 NOW()로 설정되므로 무시
    @Mapping(target = "updatedAt", ignore = true)                                               // DB에서 NOW()로 설정되므로 무시
    @Mapping(target = "lastLoginAt", ignore = true)                                             // 회원가입 시점에는 null
    @Mapping(target = "deletedAt", ignore = true)                                               // 삭제되지 않았으므로 null
    UserEntity registerRequestDtoToEntity(JoinRequestDto joinRequestDto, @Context PasswordEncoder passwordEncoder);




    @Mapping(target = "message", expression = "")
    JoinResponseDto EntityToRegisterResponseDto(UserEntity userEntity);

    /**
     * 향후 추가될 수 있는 다른 변환 메서드들:
     *
     * UserEntity updateDtoToEntity(UserUpdateDto dto);
     * UserResponseDto entityToResponseDto(UserEntity entity);
     * List<UserResponseDto> entitiesToResponseDtos(List<UserEntity> entities);
     */
}