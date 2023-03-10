package com.anubhav.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anubhav.blog.config.AppConstants;
import com.anubhav.blog.payloads.ApiResponse;
import com.anubhav.blog.payloads.PageResponse;
import com.anubhav.blog.payloads.PostDto;
import com.anubhav.blog.services.FileService;
import com.anubhav.blog.services.PostService;
import com.anubhav.blog.services.impl.FileServiceImpl;
import com.anubhav.blog.services.impl.PostServiceImpl;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postServiceImpl;

	@Autowired
	private FileService fileServiceImpl;

	@Value("${project.image}")
	private String path;

	// Create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		PostDto createPost = postServiceImpl.createPost(postDto, userId, categoryId);

		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

	}

	// get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
		List<PostDto> postByUser = postServiceImpl.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postByUser, HttpStatus.OK);
	}

	// get by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
		List<PostDto> postByCategory = postServiceImpl.getPostByCategort(categoryId);
		return new ResponseEntity<List<PostDto>>(postByCategory, HttpStatus.OK);
	}

//	get all posts
	@GetMapping("/posts")
	public ResponseEntity<List<PostDto>> getAllPosts() {
		List<PostDto> allPosts = postServiceImpl.getAllPost();
		return new ResponseEntity<List<PostDto>>(allPosts, HttpStatus.OK);
	}

//	get single post
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		PostDto post = postServiceImpl.getPost(postId);
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}

//	delete post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		postServiceImpl.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted Successfully", true), HttpStatus.OK);
	}

//	update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatePost = postServiceImpl.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

//	pagicnation
	@GetMapping("/posts/pagicnation")
	public ResponseEntity<List<PostDto>> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
		List<PostDto> allPosts = postServiceImpl.postPagicnation(pageNumber, pageSize);
		return new ResponseEntity<List<PostDto>>(allPosts, HttpStatus.OK);
	}

//	pagicnation
	@GetMapping("/posts/pagicnationResponse")
	public ResponseEntity<PageResponse> getAllPostsWithResponse(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir

	) {
		PageResponse postPagicnationWithResponse = postServiceImpl.postPagicnationWithResponse(pageNumber, pageSize,
				sortBy, sortDir);
		return new ResponseEntity<PageResponse>(postPagicnationWithResponse, HttpStatus.OK);
	}

	@GetMapping("/posts/search/{key}")
	public ResponseEntity<List<PostDto>> getPostBySearch(@PathVariable String key) {
		List<PostDto> postBySearch = postServiceImpl.getPostBySearch(key);
		return new ResponseEntity<List<PostDto>>(postBySearch, HttpStatus.OK);

	}

	// Post Image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {
		PostDto postDtos = postServiceImpl.getPost(postId);
		String uploadimage = fileServiceImpl.uploadimage(path, image);
		System.out.println(uploadimage);
		postDtos.setImageName(uploadimage);
		PostDto updatePost = postServiceImpl.updatePost(postDtos, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	@GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imagename") String imageName,
			HttpServletResponse response
			) throws IOException{
		
		InputStream resource = fileServiceImpl.getResourec(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}

}
