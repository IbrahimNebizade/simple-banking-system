package com.company.simplebankingsystem.service.concrete;

import com.company.simplebankingsystem.dao.entity.UserEntity;
import com.company.simplebankingsystem.dao.repository.AccountRepository;
import com.company.simplebankingsystem.dao.repository.UserRepository;
import com.company.simplebankingsystem.dto.request.CreateUserRequest;
import com.company.simplebankingsystem.dto.request.UpdateUserRequest;
import com.company.simplebankingsystem.dto.response.UpdateUserResponse;
import com.company.simplebankingsystem.dto.response.UserResponse;
import com.company.simplebankingsystem.exception.AlreadyExist;
import com.company.simplebankingsystem.exception.NotFoundException;
import com.company.simplebankingsystem.mapper.UserMapper;
import com.company.simplebankingsystem.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.company.simplebankingsystem.enums.UserStatus.DELETED;
import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
AccountRepository accountRepository;
    @Override
    public UserResponse createUser(CreateUserRequest request) {
        log.info("ActionLog.UserServiceImpl.createUser.start - request: {}", request);

        boolean finPresent = userRepository.findByFin(request.getFin()).isPresent();
        boolean emailPresent = userRepository.findByEmail(request.getEmail()).isPresent();

        if (finPresent) {
            throw new AlreadyExist("Fin already exist");
        }
        if (emailPresent) {
            throw new AlreadyExist("Email already exist");
        }
        UserEntity entity = UserMapper.requestToEntity(request);
        userRepository.save(entity);
        log.info("ActionLog.UserServiceImpl.createUser.end - request: {}", request);
        return UserMapper.entityToResponse(entity);
    }

    @Override
    public UpdateUserResponse updateUserById(Long id, UpdateUserRequest request) {
        log.info("ActionLog.UserServiceImpl.updateUser.start - request: {}", request);
        UserEntity entity = findUser(id);
        entity.setEmail(request.getEmail());
        entity.setUpdateAt(LocalDateTime.now());
        userRepository.save(entity);
        log.info("ActionLog.UserServiceImpl.updateUser.end - request: {}", request);
        return UserMapper.entityToUpdateResponse(entity);
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("ActionLog.UserServiceImpl.deleteUser.start - id: {}", id);
        UserEntity entity = findUser(id);
        entity.setUserStatus(DELETED);
        userRepository.save(entity);
        log.info("ActionLog.UserServiceImpl.deleteUser.end - id: {}", id);
    }

    @Override
    public UserResponse findById(Long id) {
        log.info("ActionLog.UserServiceImpl.findUser.start - id: {}", id);
        UserEntity entity =findUser(id);
        log.info("ActionLog.UserServiceImpl.findUser.end - id: {}", id);
        return UserMapper.entityToResponse(entity);
    }
    private UserEntity findUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
