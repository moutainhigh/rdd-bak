# czb-server

服务器端

1. 用户登录之后前端访问服务器，请求接口，需要用到用户信息的<br>
通过 User user = redisUtils.get(principal.getName());进行获取用户信息
<br>使用示例<br>
    @RequestMapping(value = "/test",method = RequestMethod.GET)<br>
    public JSONResult getNotificationList(Principal principal) throws InterruptedException {<br>
         &emsp;&emsp;User user = redisUtils.get(principal.getName());<br>
         &emsp;&emsp;Thread.sleep(1000);<br>
         &emsp;&emsp;return new JSONResult(user.getUserAccount());<br>
    }
2. 分页后端使用pageHelper插件，不要自己写分页代码<br>使用示例<br>
@Service<br>
public class DocServiceImpl implements IDocService {<br>
    &emsp;&emsp;@Autowired<br>
    &emsp;&emsp;private DocMapper docMapper;

   &emsp;&emsp; @Override<br>
   &emsp;&emsp; public PageInfo\<Doc> selectDocByPage1(int currentPage, int pageSize) {<br>
       &emsp;&emsp;&emsp;&emsp; PageHelper.startPage(currentPage, pageSize);<br>
        &emsp;&emsp;&emsp;&emsp;List\<Doc> docs = docMapper.selectByPageAndSelections();<br>
        &emsp;&emsp;&emsp;&emsp;PageInfo\<Doc> pageInfo = new PageInfo<>(docs);<br>
        &emsp;&emsp;&emsp;&emsp;return pageInfo;<br>
   &emsp;&emsp; }<br>
}
