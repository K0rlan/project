package kz.koko.agaionline.controllers;

import kz.koko.agaionline.models.Post;
import kz.koko.agaionline.models.User;
import kz.koko.agaionline.service.PostServiceImpl;
import kz.koko.agaionline.service.UserServiceImpl;
import kz.koko.agaionline.service.VideoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class BlogController {

    @Autowired
    private PostServiceImpl postServiceImpl;
    @Autowired
    private VideoServiceImpl videoServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;

    //GET MAPPINGS
    @GetMapping("/blog")
    public String blogMain(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Post> posts;
        if (filter != null && !filter.isEmpty()) {
            posts = postServiceImpl.findAllByTitleContainingIgnoreCase(filter);
        } else {
            posts = postServiceImpl.findAll();
        }
        model.addAttribute("title", "Блог сайта");
        model.addAttribute("posts", posts);
        model.addAttribute("filter", filter);

        return "blogMain";
    }

    @GetMapping("/myBlog")
    public String myBlogMain(@AuthenticationPrincipal User user, Model model) {
        Iterable<Post> posts = userServiceImpl.findByUsernameIgnoreCase(user.getUsername()).getPosts();
        model.addAttribute("title", "Мои курсы");
        model.addAttribute("posts", posts);
        return "myBlog";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/blog/addBlog")
    public String add(Post post, Model model) {
        model.addAttribute(post);
        model.addAttribute("title", "Добавить блог");
        return "addBlog";
    }

    @GetMapping("/blog/{id}")
    public String blogPage(@AuthenticationPrincipal User user, @PathVariable Long id, Model model) {
        if (!postServiceImpl.existsById(id)) {
            return "redirect:/blog";
        }
        User usr = userServiceImpl.findByUsernameIgnoreCase(user.getUsername());
        Post post = postServiceImpl.findById(id);
        model.addAttribute("post", post);
        model.addAttribute("videos", videoServiceImpl.findAllByPostId(post.getId()));
        model.addAttribute("title", "Курс " + post.getTitle());
        model.addAttribute("posts", usr.getPostIds());
        model.addAttribute("rePosts", usr.getRePostIds());
        return "blogPage";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/blog/{id}/editBlog")
    public String edit(@PathVariable Long id, Model model) {
        if (!postServiceImpl.existsById(id)) {
            return "redirect:/blog";
        }
        model.addAttribute("oldPost", postServiceImpl.findById(id));
        model.addAttribute("pageTitle", "Редактировать блог");
        return "editBlog";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/blog/{id}/addStudent")
    public String addStudent(@PathVariable Long id, Model model) {
        if (!postServiceImpl.existsById(id)) {
            return "redirect:/blog";
        }
        model.addAttribute("id", postServiceImpl.findById(id).getId());
        model.addAttribute("users", userServiceImpl.findAllByPostsContains(postServiceImpl.findById(id)));
        model.addAttribute("pageTitle", "Добавить студента");
        model.addAttribute("reUsers", postServiceImpl.findById(id).getRequestUsers());

        return "addStudentToBlog";
    }

    //POSTMAPPINGS
    @PostMapping("/blog/addBlog")
    public String addBlog(@AuthenticationPrincipal User user, @Valid Post post, Model model) {
        post.setAuthor(userServiceImpl.findByUsernameIgnoreCase(user.getUsername()));
        postServiceImpl.save(post);
        model.addAttribute("posts", postServiceImpl.findAll());
        return "redirect:/blog";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/blog/{id}/editBlog")
    public String editBlog(@AuthenticationPrincipal User user, @Valid Post post, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "editBlog";
        }
//        post.setAuthor();
        postServiceImpl.save(post);
        model.addAttribute("posts", postServiceImpl.findAll());
        return "redirect:/blog";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/blog/{id}/deleteBlog")
    public String deleteBlog(@PathVariable Long id) {
        postServiceImpl.delete(postServiceImpl.findById(id));
        return "redirect:/blog";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/blog/addToPost/{id}")
    public String addToPost(@AuthenticationPrincipal User user, @PathVariable Long id) {
        if (!postServiceImpl.existsById(id)) {
            return "redirect:/blog";
        }
        User usr = userServiceImpl.findByUsernameIgnoreCase(user.getUsername());
        usr.getRequestPosts().add(postServiceImpl.findById(id));
        userServiceImpl.save(usr);

        return "redirect:/blog/{id}";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/blog/b/{id}")
    public String backToPost(@AuthenticationPrincipal User user, @PathVariable Long id) {
        if (!postServiceImpl.existsById(id)) {
            return "redirect:/blog";
        }
        User usr = userServiceImpl.findByUsernameIgnoreCase(user.getUsername());
        usr.getRequestPosts().remove(postServiceImpl.findById(id));
        userServiceImpl.save(usr);

        return "redirect:/blog/{id}";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/blog/{id}/addStudent")
    public String addStudentToBlog(@PathVariable Long id, @RequestParam String username, Model model) {
        if (!postServiceImpl.existsById(id)) {
            return "redirect:/blog";
        }
        if (userServiceImpl.findByUsernameIgnoreCase(username) == null) {
            model.addAttribute("message", "Такого студента не существует");
            return "redirect:/blog/{id}/addStudent";
        }
        User user = userServiceImpl.findByUsernameIgnoreCase(username);
        if (user.getPosts().contains(postServiceImpl.findById(id))) {
            model.addAttribute("message", "Такой студент уже есть");
            return "redirect:/blog/{id}/addStudent";
        }
        user.getPosts().add(postServiceImpl.findById(id));
        userServiceImpl.save(user);
        return "redirect:/blog/{id}/addStudent";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/blog/{id}/addStudent/{userid}")
    public String addStudentToBlog(@PathVariable Long id, @PathVariable Long userid, Model model) {
        if (!postServiceImpl.existsById(id)) {
            return "redirect:/blog";
        }
        if (userServiceImpl.findById(userid) == null) {
            model.addAttribute("message", "Такого студента не существует");
            return "redirect:/blog/{id}/addStudent";
        }
        User user = userServiceImpl.findById(userid);
        if (user.getPosts().contains(postServiceImpl.findById(id))) {
            model.addAttribute("message", "Такой студент уже есть");
            return "redirect:/blog/{id}/addStudent";
        }
        user.getPosts().add(postServiceImpl.findById(id));
        user.getRequestPosts().remove(postServiceImpl.findById(id));
        userServiceImpl.save(user);
        return "redirect:/blog/{id}/addStudent";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/blog/{id}/deleteStudent/{username}")
    public String deleteStudentFromBlog(@PathVariable Long id, @PathVariable String username) {
        if (!postServiceImpl.existsById(id)) {
            return "redirect:/blog";
        }
        User user = userServiceImpl.findByUsernameIgnoreCase(username);
        user.getPosts().remove(postServiceImpl.findById(id));
        userServiceImpl.save(user);

        return "redirect:/blog/{id}/addStudent";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/blog/{id}/deleteReStudent/{userid}")
    public String deleteStudetFromRequest(@PathVariable Long id, @PathVariable Long userid) {
        if (!postServiceImpl.existsById(id)) {
            return "redirect:/blog";
        }
        User user = userServiceImpl.findById(userid);
        user.getRequestPosts().remove(postServiceImpl.findById(id));
        userServiceImpl.save(user);

        return "redirect:/blog/{id}/addStudent";
    }
}
