package springboot.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import springboot.project.dao.BlogRepository;
import springboot.project.dao.DoctorUserRepository;
import springboot.project.entity.Blog;
import springboot.project.entity.DoctorUser;
import springboot.project.model.BlogDTO;
import springboot.project.model.UserPrincipal;
import springboot.project.service.BlogService;
import springboot.project.utils.DateUtils;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    DoctorUserRepository doctorUserRepository;
    @Override
    public Blog create(BlogDTO dto) {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        DoctorUser doctorUser = doctorUserRepository.findByUser_Id(currentUser.getId());


        Blog newBlog = new Blog(
            doctorUser.getId(),
                dto.getDoctorName(),
                dto.getContent(),
                dto.getSpecializationIdList(),
                dto.getView(),
                dto.getShare(),
                DateUtils.now(),
                dto.getAttachments()
        );

        newBlog = blogRepository.save(newBlog);

        return newBlog;
    }

    @Override
    public Blog update(BlogDTO dto) {
        Blog oldBLog = blogRepository.findById(dto.getId()).get();

        if (oldBLog == null)
            throw new RuntimeException("id blog not found");

        oldBLog.setDoctorName(dto.getDoctorName());
        oldBLog.setContent(dto.getContent());
        oldBLog.setSpecializationIdList(dto.getSpecializationIdList());
        oldBLog.setView(dto.getView());
        oldBLog.setShare(dto.getShare());
        oldBLog.setUpdateAt(DateUtils.now());
        oldBLog.setAttachments(dto.getAttachments());
        oldBLog = blogRepository.save(oldBLog);

        return oldBLog;
    }

    @Override
    public Blog findById(Integer blogId) {
        return blogRepository.findById(blogId).get();
    }

    @Override
    public Page<Blog> findAllByDoctorId(Pageable pageable, Integer doctorId) {
        return blogRepository.findAllByDoctorId(pageable, doctorId);
    }

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }
}
