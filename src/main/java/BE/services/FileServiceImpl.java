package BE.services;

import BE.entities.project.File;
import BE.exceptions.FileNotFoundException;
import BE.repositories.FileRepository;
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
    @Override
    @Transactional
    public File createFile(String projectName, String filePath) {
//        if (this.getFile(projectName,filePath).getPath() != null) throw new UserAlreadyExistsException();
        File file;
        String file_name = filePath.substring(filePath.lastIndexOf("/"));
        //TODO may want to change distinguishing between file and dir?
        if(filePath.contains("."))
            file = new File(filePath,file_name, "file");
        else
            file = new File(filePath,file_name, "dir");
        FileRepository.save(file);
        return file;
    }
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
    @Override
    @Transactional
    public File deleteFile(String projectName, String filePath) {
        File file = this.getFile(projectName, filePath);
        if (file == null) throw new FileNotFoundException();
        FileRepository.delete(file);
        return file;
    }
//
//    @Override
//    public List<Privilege> getAllPrivileges() {
//        return (List<Privilege>) privilegeRepository.findAll();
//    }
}
