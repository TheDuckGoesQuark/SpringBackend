package BE.services;

import BE.entities.project.File;
import BE.entities.user.Privilege;
import BE.entities.user.User;
import BE.exceptions.FileNotFoundException;
import BE.exceptions.UserNotFoundException;
import BE.exceptions.UserAlreadyExistsException;
import BE.repositories.FileRepository;
import BE.repositories.PrivilegeRepository;
import BE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository FileRepository;

//    @Autowired
//    ProjectRepository projectRepository;

    @Override
    public List<File> getAllFiles(String projectName) {
        List<File> files = new ArrayList<>();
        File root_dir = FileRepository.findByProjectName(projectName);
        FileRepository.findAll().forEach( file->{if(file.getPath().startsWith(root_dir.getPath())) files.add(file);});
        return files;
    }

    @Override
    public File getFile(String projectName, String filePath) {
        List<File> files = this.getAllFiles(projectName);
        for(File file : files)
            if(file.getPath().equals(filePath))
                return file;
        throw new FileNotFoundException();
    }

//    @Override
//    public User getUserByUserName(String username) {
//        User user = userRepository.findByUsername(username);
//        if (user == null) throw new UserNotFoundException();
//        else return user;
//    }
//
//    @Override
//    @Transactional
//    public User createUser(User user) {
//        if (userRepository.findByUsername(user.getUsername()) != null) throw new UserAlreadyExistsException();
//        userRepository.save(user);
//        return user;
//    }
//
//    @Override
//    @Transactional
//    public User updateUser(User user) {
//        if (userRepository.findByUsername(user.getUsername()) == null) throw new UserNotFoundException();
//        // .save performs both update and creation
//        userRepository.save(user);
//        return user;
//    }
//
//    @Override
//    @Transactional
//    public User deleteUser(String username) {
//        if (userRepository.findByUsername(username) == null) throw new UserNotFoundException();
//        return userRepository.deleteByUsername(username);
//    }
//
//    @Override
//    public List<Privilege> getAllPrivileges() {
//        return (List<Privilege>) privilegeRepository.findAll();
//    }
}
