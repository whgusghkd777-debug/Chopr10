@GetMapping("/list")
public String list(Model model) {
    model.addAttribute("content", "music/list :: content");
    return "layout";
}