package BackendProject.controllers;

import BackendProject.dto.EditUser;
import BackendProject.dto.SearchUser;
import BackendProject.dto.request.RequestData;
import BackendProject.entities.User;
import BackendProject.services.UserService;
import BackendProject.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('user:read')")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = {"application/json"})
    public List<User> getAll(){
        return userService.getAll();
    }

    @PreAuthorize("hasAuthority('user:read')")
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = {"application/json"})
    public Slice<User> search(@RequestBody RequestData<SearchUser> rd) {
        return userService.search(rd.getData(), rd.getPaging());
    }

    @PreAuthorize("hasAuthority('user:read')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json"})
    public User getById(@PathVariable Long id) throws Exception {
        return userService.findById(id);
    }

    @PreAuthorize("hasAuthority('user:edit')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = {"application/json"})
    public User edit(@PathVariable Long id, @RequestBody EditUser editUser) throws Exception {
        GeneralUtil.checkRequiredProperties(editUser, Arrays.asList("email"));
        return userService.edit(id, editUser);
    }
}
