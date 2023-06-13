package BackendProject.services;

import BackendProject.util.GeneralUtil;
import BackendProject.dto.EditUser;
import BackendProject.dto.SearchUser;
import BackendProject.dto.request.Paging;
import BackendProject.entities.User;
import BackendProject.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepositiory;


    public List<User> getAll(){
        return userRepositiory.findAll();
    }

    public User getById(Long id) throws Exception {
        return userRepositiory.findById(id).orElseThrow(() -> new ResourceNotFoundException("RECORD_NOT_FOUND"));
    }

    public Slice<User> search(SearchUser searchUser, Paging paging){
        String email = null;

        if(searchUser.getEmail() != null && !searchUser.getEmail().equals("")){
            email = "%" + searchUser.getEmail() + "%";
        }

        Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by("age").ascending());
        return userRepositiory.search(searchUser.getUserId(), email, pageable);
    }

    public User findById(Long id) throws Exception{
        return userRepositiory.findById(id).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));
    }

    @Transactional
    public User edit(Long id, EditUser editUser) throws Exception{
        User user = userRepositiory.findById(id).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));
        GeneralUtil.getCopyOf(editUser,user);

        return userRepositiory.save(user);
    }


}
