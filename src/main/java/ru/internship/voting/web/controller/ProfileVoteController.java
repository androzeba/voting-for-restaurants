package ru.internship.voting.web.controller;

import static ru.internship.voting.util.ValidationUtil.checkNotFoundWithId;
import static ru.internship.voting.web.SecurityUtil.authUserId;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.internship.voting.model.Vote;
import ru.internship.voting.repository.VoteRepository;
import ru.internship.voting.util.DateTimeUtil;
import ru.internship.voting.util.exception.IllegalRequestDataException;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController {

  private static final Logger log = LoggerFactory.getLogger(ProfileVoteController.class);

  private final VoteRepository voteRepository;

  @Autowired
  public ProfileVoteController(VoteRepository voteRepository) {
    this.voteRepository = voteRepository;
  }

  @GetMapping("/votes")
  public List<Vote> getAllVotes() {
    log.info("Get all votes for user {}", authUserId());
    return voteRepository.getAll(authUserId());
  }

  @GetMapping("/votes/filter")
  public List<Vote> getFilteredVotes(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    log.info("Get all votes for user {} between dates ({} - {})", authUserId(), startDate, endDate);
    return voteRepository.getBetween(DateTimeUtil.dateOrMin(startDate), DateTimeUtil.dateOrMax(endDate), authUserId());
  }

  @PostMapping("/votes")
  public ResponseEntity<Vote> createVote(@RequestParam int restId) {
    LocalDate date = LocalDate.now();
    Vote currentVote = voteRepository.getByDateAndUserId(date, authUserId());
    if (currentVote == null) {
      Vote vote = new Vote(date);
      log.info("Create new vote for user {}", authUserId());
      Vote created = voteRepository.save(vote, authUserId(), restId);
      URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
          .path("/votes")
          .buildAndExpand(created.getId())
          .toUri();
      return ResponseEntity.created(uriOfNewResource).body(created);
    }
    throw new IllegalRequestDataException("vote already exists for logged user");
  }

  @PutMapping("/votes")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void updateVote(@RequestParam int restId) {
    LocalDate date = LocalDate.now();
    Vote currentVote = voteRepository.getByDateAndUserId(date, authUserId());
    Assert.notNull(currentVote, "vote for update not exists");
    if (DateTimeUtil.isTimeToUpdate()) {
      log.info("Update vote for user {}", authUserId());
      checkNotFoundWithId(voteRepository.save(currentVote, authUserId(), restId), currentVote.getId());
    } else {
      throw new IllegalRequestDataException("no more voting available today");
    }
  }
}
