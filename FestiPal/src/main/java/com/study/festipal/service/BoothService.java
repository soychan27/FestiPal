package com.study.festipal.service;

import com.study.festipal.entity.booth;
import com.study.festipal.repository.BoothRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class BoothService {

    @Autowired
    private BoothRepository boothRepository;

    @GetMapping("/booth/list")
    public List<booth> boothList(){
        return boothRepository.findAll();
    }

    public void write(booth booth, MultipartFile file) throws Exception{
        if (!file.isEmpty()){
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

            if(!Files.exists(Paths.get(projectPath))){
                Files.createDirectories(Paths.get(projectPath));
            }

            UUID uuid = UUID.randomUUID();
            String imagename = uuid + "_" + file.getOriginalFilename();
            File savefile = new File(projectPath, imagename);
            file.transferTo(savefile);

            booth.setImagename(imagename);
            booth.setImagepath("/files/" + imagename);
        }

        boothRepository.save(booth);
    }

    public booth boothView(Integer id) {
        return boothRepository.findById(id).get();
    }

    public void boothDelete(Integer id) {
        boothRepository.deleteById(id);
    }

    public void save(booth booth) {
        boothRepository.save(booth);
    }

}
