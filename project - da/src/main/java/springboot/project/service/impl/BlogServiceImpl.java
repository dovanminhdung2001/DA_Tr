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


        Blog newBlog = new Blog();
        newBlog.setDoctorId(doctorUser.getId());
        newBlog.setDoctorName(doctorUser.getUser().getName());
        newBlog.setTitle(dto.getTitle());
        newBlog.setThumbnail(dto.getThumbnail());
        newBlog.setContent(dto.getContent());
        newBlog.setSpecializationIdList(dto.getSpecializationIdList());
        newBlog.setCreateAt(DateUtils.now());

        newBlog = blogRepository.save(newBlog);

        return newBlog;
    }

    @Override
    public Blog update(BlogDTO dto) {
        Blog oldBLog = blogRepository.findById(dto.getId()).get();

        if (oldBLog == null)
            throw new RuntimeException("id blog not found");

        oldBLog.setDoctorName(dto.getDoctorName());
        oldBLog.setTitle(dto.getTitle());
        oldBLog.setThumbnail(dto.getThumbnail());
        oldBLog.setContent(dto.getContent());
        oldBLog.setSpecializationIdList(dto.getSpecializationIdList());
        oldBLog.setView(dto.getView());
        oldBLog.setShare(dto.getShare());
        oldBLog.setUpdateAt(DateUtils.now());

        oldBLog = blogRepository.save(oldBLog);

        return oldBLog;
    }

    @Override
    public Blog findById(Integer blogId) {
        Blog blog = blogRepository.findById(blogId).get();

        blog.setView(blog.getView() + 1);

        return blogRepository.save(blog);
    }

    @Override
    public Page<Blog> findAllByDoctorId(Pageable pageable, Integer doctorId, Boolean isActive) {
        return isActive == null
                ? blogRepository.findAllByDoctorId(pageable, doctorId)
                : blogRepository.findAllByDoctorIdAndIsActive(pageable, doctorId, isActive);
    }

    @Override
    public Page<Blog> findAll(Pageable pageable, Boolean isActive) {
        return isActive == null
                ? blogRepository.findAll(pageable)
                : blogRepository.findAllByIsActive(pageable, isActive);
    }

    @Override
    public Blog delete(Integer id, Boolean status) {
        Blog blog = blogRepository.findById(id).get();

        if (blog == null)
            throw new RuntimeException("id not found");

        blog.setIsActive(status);
        return blogRepository.save(blog);
    }

    @Override
    public Page<Blog> findAllByTitle(Pageable pageable, String title) {
        return blogRepository.findAllByTitleContainingIgnoreCaseAndIsActiveTrue(pageable, title);
    }

    @Override
    public Page<Blog> findAllBySpecializationId(Pageable pageable, String specializationId) {
        return blogRepository.findAllBySpecializationIdList(pageable, specializationId);
    }
}
