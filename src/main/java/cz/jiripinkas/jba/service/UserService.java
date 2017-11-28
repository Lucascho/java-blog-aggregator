package cz.jiripinkas.jba.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import cz.jiripinkas.jba.entity.Blog;
import cz.jiripinkas.jba.entity.Item;
import cz.jiripinkas.jba.entity.User;
import cz.jiripinkas.jba.repository.BlogRepository;
import cz.jiripinkas.jba.repository.ItemRepository;
import cz.jiripinkas.jba.repository.UserRepository;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userRepository;
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	public User findOne(int id) {
		return userRepository.findOne(id);
	}
	
	public void save(User user) {
		userRepository.save(user);
	}

	
	@Transactional
	public User findOnewithBlogs(int id) {
		User user = findOne(id);
		List<Blog>blogs = blogRepository.findByUser(user);
		for (Blog blog : blogs) {
			List<Item>items = itemRepository.findByBlog(blog,new PageRequest(0, 10, Direction.DESC, "publishedDate"));
			blog.setItems(items);
		}
		user.setBlogs(blogs);
		return user;
	}


}
