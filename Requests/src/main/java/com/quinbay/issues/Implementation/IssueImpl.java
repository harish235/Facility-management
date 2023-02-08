package com.quinbay.issues.Implementation;

import com.quinbay.issues.Pojo.*;
import com.quinbay.issues.service.IssuesService;
import com.quinbay.issues.model.*;
import com.quinbay.issues.repository.CategoryRepository;
import com.quinbay.issues.repository.IssueHistoryRepository;
import com.quinbay.issues.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class IssueImpl implements IssuesService {

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    IssueHistoryRepository issueHistoryRepository;

    @Autowired
    CategoryRepository categoryRepository;

//    @Autowired
//    MailingImpl mailingImpl;

    public String determinePriority(double sla){

        if(sla <= 0.5){return "IMMEDIATE" ;}
        else if(sla <= 2){return "HIGH";}
        else if(sla <=6){return "MEDIUM";}
        else if(sla > 6){return "LOW"; }
        return "";
    }

    @Override
    public ReturnData addIssue(IssuesInput issuesInput) {
        ReturnData returnData = new ReturnData();
        try {
            if(Integer.parseInt(issuesInput.getAssigneeUserId()) < 0 || Double.parseDouble(issuesInput.getSla())<0.2){ returnData.setStatus(false); returnData.setMessage("User ID and SLA cannot be negative"); return returnData; }
            List<IssueHistory> newList = new ArrayList<>();
            float hours =Float.parseFloat(issuesInput.getSla());
            int minutes = (int)Math.round(hours)*60;
            Issues issue = new Issues(Integer.parseInt(issuesInput.getAssigneeUserId()), issuesInput.getType(), issuesInput.getCategory(), issuesInput.getSla(), issuesInput.getDescription());
            IssueHistory issueHistory = new IssueHistory(Integer.parseInt(issuesInput.getAssigneeUserId()), issuesInput.getType(), issuesInput.getCategory(), issuesInput.getSla(), issuesInput.getDescription());
            String priority = determinePriority(Double.parseDouble(issuesInput.getSla()));
            issue.setPriority(priority);  issueHistory.setPriority(priority);
            issue.setStatus(StatusType.OPEN);  issueHistory.setStatus(StatusType.OPEN);
            Date now = new Date();
            issue.setCreatedDate(now);  issueHistory.setCreatedDate(now);
            Date deadline = addMinutesToDate(minutes, now);
            issue.setDeadlineDate(deadline);   issueHistory.setDeadlineDate(deadline);
            issue.setUpdatedDate(now);   issueHistory.setUpdatedDate(now);
            issue.setIssuehistory(newList);
            issueRepository.save(issue);
            issueHistory.setIssueId(issue.getId());
            issue.getIssuehistory().add(issueHistory);
            issueRepository.save(issue);
            if(issue.getPriority() == "IMMEDIATE") {
//                mailingImpl.mailForImmediates(issue.getId(), issue.getAssigneeUserId());
            }
            returnData.setStatus(true);  returnData.setMessage("Issue added successfully!");
        }
        catch (Exception e) {
            System.out.println(e);
            returnData.setStatus(false);  returnData.setMessage("Something went wrong. Kindly enter the issue details correctly!");
        }
        return returnData;
    }

    @Override
    public List<Issues> viewIssuesByAssigneeId(int assigneeUserId) {
        try {
            List<Issues> issues = issueRepository.findByAssigneeUserId(assigneeUserId);
//            if(!issues.isEmpty()) { return issues; }
            return issues;
        }
        catch (Exception e) {
            System.out.println(e);
            List<Issues> list = new ArrayList<>();
            return list;
        }
    }

    @Override
    public ReturnData viewIssues() {
        ReturnData returnData = new ReturnData();
        try {
            returnData.setStatus(true);
            returnData.setMessage("Data fetched successfully");
            returnData.setIssues(issueRepository.findAll(Sort.by(Sort.Direction.ASC, "updatedDate")));
        }
        catch (Exception e) {
            returnData.setStatus(false);
            returnData.setMessage("Something went wrong. Unable to fetch the details!");
        }
        return returnData;
    }

    @Override
    public ReturnData viewIssuesByRole(int id, String role){
        ReturnData returnData = new ReturnData();
        try{
            List<Issues> issues = new ArrayList<>();
            if(role.equals("Developer")){
                issues = issueRepository.findByAssigneeUserId(id);
                returnData.setStatus(true); returnData.setMessage("Data fetched successfully"); returnData.setIssues(issues); returnData.setCount(issues.size());
            }
            else if(role.equals("Admin")){
                List<Issues> adminIssues = new ArrayList<>();
                List<Issues> allIssues = issueRepository.findAll();
                for(Issues issue: allIssues){
                    if(!issue.getStatus().equals(StatusType.DELETE)){ adminIssues.add(issue); }
                }
                returnData.setStatus(true); returnData.setMessage("Data fetched successfully"); returnData.setIssues(adminIssues); returnData.setCount(adminIssues.size());
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            returnData.setStatus(false); returnData.setMessage("Something went wrong. Unable to fetch the details!");
        }
        return returnData;
    }

//    @Override
//    public List<Issues> viewClosedIssues() {
//        try {
//            List<Issues> issues = issueRepository.findByStatus(StatusType.CLOSED);
////            if(!issues.isEmpty()) { return issues; }
//        return  issues;
//        }
//        catch (Exception e) {
//            System.out.println(e);
//            List<Issues> list = new ArrayList<>();
//            return list;
//        }
//    }

    @Override
//    public ReturnData updateStatus(int issueId, String status) {
    public ReturnData updateStatus(int issueId, StatusType status) {
        HashMap<StatusType, Integer> statuses = new HashMap<StatusType, Integer>();
        System.out.println(issueId);
        statuses.put(StatusType.OVERDUE, 0); statuses.put(StatusType.OPEN, 1); statuses.put(StatusType.REOPEN, 2); statuses.put(StatusType.REVIEWED, 3);statuses.put(StatusType.COMPLETED, 4);
        ReturnData returnData = new ReturnData();
        Date now = new Date();
        Optional<Issues> updateIssue = issueRepository.findById(issueId);
        try {
            if(updateIssue.isPresent()) {
                Issues issue = updateIssue.get();
                if(statuses.get(updateIssue.get().getStatus()) <= statuses.get(status)) {
                    IssueHistory issueHistory = new IssueHistory(issue.getId(), issue.getAssigneeUserId(), issue.getIssueType(), issue.getIssueCategory(), issue.getSla(), issue.getDescription(), issue.getPriority(), status, issue.getCreatedDate(), issue.getDeadlineDate(), issue.getUpdatedDate());
                    issue.setStatus(status); issue.getIssuehistory().add(issueHistory);
                    issueRepository.save(issue);
                    returnData.setStatus(true); returnData.setMessage("Status updated!");
                }
                else { returnData.setStatus(false); returnData.setMessage("Something went wrong. Kindly enter the issue status correctly!"); }
            }
        }
        catch (Exception e) {
            returnData.setStatus(false); returnData.setMessage("Something went wrong. Kindly enter the issue id correctly!");
        }
        return returnData;
    }

    @Override
    public ReturnData reopenIssue(int userId, int issueId){
        Date now = new Date();
        ReturnData returnData = new ReturnData();
        Optional<Issues> issueOp = issueRepository.findByAssigneeUserIdAndId(userId, issueId);
        Issues issue = issueOp.get();
        if(issue.getStatus().equals(StatusType.CLOSED)){
            Date closedDate = issue.getUpdatedDate();

            String categoryName = issue.getIssueCategory();
            Optional<Categories> category = categoryRepository.findByCategoryName(categoryName);
            int validityTime = category.get().getValidityTime();
            int dayDiff = findDayDifference(closedDate, now);
            if(dayDiff >= validityTime){
                System.out.println("\nCannot be reopened");
                returnData.setStatus(false);
                returnData.setMessage("The issue cannot be reopened, as the issue has not been closed!!!");
            }
            else{
                System.out.println("\n can be reopened");
                IssueHistory issueHistory = new IssueHistory(issue.getId(), issue.getAssigneeUserId(), issue.getIssueType(), issue.getIssueCategory(), issue.getSla(), issue.getDescription(), issue.getPriority(), StatusType.OPEN,  issue.getCreatedDate(), issue.getDeadlineDate(), issue.getUpdatedDate());

                issue.setStatus(StatusType.REOPEN);
                issue.getIssuehistory().add(issueHistory);
                issueRepository.save(issue);
                returnData.setStatus(true);
                returnData.setMessage("Status updated!");
            }
        }
        returnData.setStatus(false);    returnData.setMessage("The issue cannot be reopened, as the issue has not been closed!!!");
        return returnData;
    }

    @Override
    public ReturnData closeIssue(int issueId) {
        ReturnData returnData = new ReturnData();
        try {
            Date now = new Date();
            Optional<Issues> issueOp = issueRepository.findById(issueId);
            Issues issue = issueOp.get();
            if(issueOp.isPresent()) {
                if(issue.getStatus().equals(StatusType.COMPLETED)) {
                    IssueHistory issueHistory = new IssueHistory(issue.getId(), issue.getAssigneeUserId(), issue.getIssueType(), issue.getIssueCategory(), issue.getSla(), issue.getDescription(), issue.getPriority(), StatusType.CLOSED,  issue.getCreatedDate(), issue.getDeadlineDate(), issue.getUpdatedDate());
                    issue.setStatus(StatusType.CLOSED);
                    issue.setUpdatedDate(now);
                    issue.getIssuehistory().add(issueHistory);
                    issueRepository.save(issue);

                    returnData.setStatus(true);
                    returnData.setMessage("Issue closed!");
                }
                else { returnData.setStatus(false); returnData.setMessage("Issue has not been marked completed by admin yet. You cannot close the issue now!"); }
            }
            else { returnData.setStatus(false); returnData.setMessage("Something went wrong. Incorrect issue id!"); }
        }
        catch (Exception e) {
            System.out.println(e);
            returnData.setStatus(false); returnData.setMessage("Something went wrong. Couldn't close the issue!");
        }
        return returnData;
    }

    @Override
    public ReturnData addComments(int issueId, String comment) {
        ReturnData returnData = new ReturnData();
        try {
            Optional<Issues> issue = issueRepository.findById(issueId);
            if(issue.isPresent()) {
//                issue.get().setComments(comment);
                issueRepository.save(issue.get());
                returnData.setStatus(true);
                returnData.setMessage("Comments added!");
            }
            else {
                returnData.setStatus(false);
                returnData.setMessage("Something went wrong. Couldn't find the issue!!!");
            }
        }
        catch (Exception e) {
            returnData.setStatus(false);
            returnData.setMessage("Something went wrong. Couldn't add the comments!!!");
        }
        return returnData;
    }

    @Override
    public HashMap<String, Integer> countOfStatus() {
        HashMap<String, Integer> count = new HashMap<String, Integer>();
        count.put("OPEN", issueRepository.findByStatus(StatusType.OPEN).size());
        count.put("REVIEWED", issueRepository.findByStatus(StatusType.REVIEWED).size());
        count.put("COMPLETED", issueRepository.findByStatus(StatusType.COMPLETED).size());
        count.put("OVERDUE", issueRepository.findByStatus(StatusType.OVERDUE).size());
        count.put("CLOSED", issueRepository.findByStatus(StatusType.CLOSED).size());
        count.put("REOPEN", issueRepository.findByStatus(StatusType.REOPEN).size());
        count.put("DELETED", issueRepository.findByStatus(StatusType.REOPEN).size());

        return count;
    }

    @Override
    public ReturnData deleteIssue(int issueId) {
        ReturnData returnData = new ReturnData();
        try {
            Date now = new Date();
            Optional<Issues> issueOp = issueRepository.findById(issueId);
            Issues issue = issueOp.get();
            if(issueOp.isPresent()) {
                if(issue.getStatus().equals(StatusType.OPEN)) {
                    IssueHistory issueHistory = new IssueHistory(issue.getId(), issue.getAssigneeUserId(), issue.getIssueType(), issue.getIssueCategory(), issue.getSla(), issue.getDescription(), issue.getPriority(), StatusType.DELETE, issue.getCreatedDate(), issue.getDeadlineDate(), issue.getUpdatedDate());
                    issue.setStatus(StatusType.DELETE);
                    issue.setUpdatedDate(now);
                    issue.getIssuehistory().add(issueHistory);
                    issueRepository.save(issue);
                    returnData.setStatus(true); returnData.setMessage("Issue deleted!");
                }
                else { returnData.setStatus(false);    returnData.setMessage("The issue has to be in OPEN state to be deleted!"); }
            }
            else { returnData.setStatus(false);    returnData.setMessage("Something went wrong. Couldn't delete!"); }
        }
        catch (Exception e) {
            returnData.setStatus(false);    returnData.setMessage("Something went wrong. Couldn't delete!");
        }
        return returnData;
    }

    @Override
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        List<Categories> categoryList = categoryRepository.findAll();
        for(Categories category: categoryList){
            categories.add(category.getCategoryName());
        }
        return categories;
    }
//
    @Override
    public List<IssueResponse> viewByStatus(StatusType status) {
        List<Issues> issuesList = issueRepository.findByStatus(status);
        List<IssueResponse> issueResponses = new ArrayList<>();
        for(Issues issue: issuesList){
            IssueResponse issueResponse = new IssueResponse(issue.getId(), issue.getAssigneeUserId(), issue.getIssueType(), issue.getIssueCategory(), issue.getSla(), issue.getDescription(), issue.getPriority(), issue.getStatus(), issue.getCreatedDate(), issue.getDeadlineDate(),issue.getUpdatedDate());
            issueResponses.add(issueResponse);
        }
        return issueResponses;
    }

    @Override
    public List<Issues> viewByCategories(String category) {
        return issueRepository.findByIssueCategory(category);
    }

    @Override
    public ReturnData addCategory(String category, int validity) {
        ReturnData returnData = new ReturnData();
        System.out.println(categoryRepository.findByCategoryName(category).equals(null));
        Optional<Categories> categorycheck = categoryRepository.findByCategoryName(category);
        if(!categorycheck.isPresent()) {
            Categories categories = new Categories();
            categories.setCategoryName(category);
            categories.setValidityTime(validity);
            categoryRepository.save(categories);
            returnData.setStatus(true);
            returnData.setMessage("Category added successfully!");
        }
        else { returnData.setStatus(false); returnData.setMessage("Category already exists"); }
        return returnData;
    }

    @Override
    public ResponseEntity addChat(ChatDetails chatDetails){
        Date now = new Date();
        try {
            Chat newchat = new Chat(chatDetails.getIssueId(), chatDetails.getText(), chatDetails.getSenderId());
            newchat.setMsgType("msg");
            newchat.setTimestamp(now);
            Optional<Issues> issue = issueRepository.findById(chatDetails.getIssueId());
            issue.get().getChatList().add(newchat);
            issueRepository.save(issue.get());
            return new ResponseEntity("chat added", HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity("chat could not be added", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ReturnData getChat(Integer issueId){
        ReturnData returnData = new ReturnData();
        try{
            Optional<Issues> issues = issueRepository.findById(issueId);
            List<Chat> chatList = issues.get().getChatList();
//            List<ChatResponse> chatResponseList = new ArrayList<>();
            List<ChatResponse> chatResponseList =
                    chatList.stream()
                            .map(objects -> {
                                ChatResponse chatResponse = new ChatResponse(objects.getIssueId(), objects.getMsgType(), objects.getText(), objects.getSenderId(), objects.getTimestamp());
                                return chatResponse;
                            })
                            .collect(Collectors.toList());
            returnData.setStatus(true);
            returnData.setMessage("Chats retrieved successfully!!!");
            returnData.setChatList(chatResponseList);
            return returnData;
        }catch(Exception e){
            returnData.setStatus(false);
            returnData.setMessage("Chat cannot be retrieved!!!");
            return returnData;
        }
    }

    @Override
    public List<Issues> viewByPriority(String priority) {
        return issueRepository.findByPriority(priority);
    }

    @Override
    public List<Issues> checkForOverdues() {
        List<Issues> issues = issueRepository.findAll();
        List<Issues> filteredIssues = issues.stream()
                .filter(item -> (!item.getPriority().equals("DELETED") && !item.getPriority().equals("CLOSED")))
                .collect(Collectors.toList());
        Date now = new Date();
        if(!filteredIssues.isEmpty()) {
            for(Issues issue: issues) {
                int result = now.compareTo(issue.getDeadlineDate());

                if(result > 0) {
//                    issue.setStatus("OVERDUE");
                    issue.setStatus(StatusType.OVERDUE);
                    System.out.println("\n\t OVERDUE");
//                    mailingImpl.mailForOverdue(issue.getIssueId(), issue.getAssigneeUserId());
                }
            }
        }

        for(Issues issue: filteredIssues){
            System.out.println("\n\t "+ issue.getPriority());
        }
        return filteredIssues;

//        List<Issues> overdueIssues = issueRepository.findByStatus("OVERDUE");
//        return overdueIssues;
    }

    @Override
    public ReturnData checkForIncomplete() {
        List<Issues> issues = issueRepository.findAll();
        Date now = new Date();
        if(!issues.isEmpty()) {
            for(Issues issue : issues) {
                int result = now.compareTo(issue.getDeadlineDate());
                if(result > 0) {
                    System.out.println("\n the issue is not resolved");
                }
            }
        }
        ReturnData returnData = new ReturnData();
        returnData.setStatus(true);
        returnData.setMessage("Overdue data fetched");
//        returnData.setIssues(issueRepository.findByStatus("COMPLETED"));
        returnData.setIssues(issueRepository.findByStatus(StatusType.COMPLETED));

        return returnData;
    }

    @Override
    public List<IssueHistory> viewIssueHistory(int issueId){
        List<IssueHistory> issues = new ArrayList<>();
        try {
            issues = issueHistoryRepository.findByIssueIdOrderByUpdatedDate(issueId);
        }catch(Exception e){
            System.out.println();
        }
        return issues;
    }

    @Override
    public ResponseEntity<Map<String, Object>> displayByPages(int pageNo, int pageSize, int id, String role) {

        ReturnData returnData = new ReturnData();
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("updatedDate").descending());
        try{
            List<Issues> issues = new ArrayList<>();
            System.out.println(role);
            Map<String, Object> response = new HashMap<>();
            Page<Issues> pagedResult;
            if(role.equals("Developer")) {
                pagedResult = issueRepository.findByAssigneeUserId(id, paging);
            }else{
                pagedResult = issueRepository.findAll(paging);
            }
            response.put("Issues", pagedResult.getContent());
            response.put("currentPage", pagedResult.getNumber());
            response.put("totalItems", pagedResult.getTotalElements());
            response.put("totalPages", pagedResult.getTotalPages());
            return new ResponseEntity<>(response , HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public int findDayDifference(Date start, Date end){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(start);
        cal2.setTime(end);
        long millis = cal2.getTimeInMillis() - cal1.getTimeInMillis();
        int days = (int) (millis / (24 * 60 * 60 * 1000));
        return days;
    }

    static final long ONE_MINUTE_IN_MILLIS = 60000;

    public static Date addMinutesToDate(int minutes, Date beforeTime) {

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs
                + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }
}
