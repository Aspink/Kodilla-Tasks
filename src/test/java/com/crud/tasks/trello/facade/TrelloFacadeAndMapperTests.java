package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TrelloFacadeAndMapperTests {
    @Autowired
    private TrelloFacade trelloFacade;

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void mapToBoardDtoAndMapToListDtoTest() {
        //Given
        List<TrelloList> trelloLists1 = new ArrayList<>();
        trelloLists1.add(new TrelloList("1001", "List11", true));
        trelloLists1.add(new TrelloList("1002", "List12", false));
        trelloLists1.add(new TrelloList("1003", "List13", false));
        trelloLists1.add(new TrelloList("1004", "List14", true));

        List<TrelloList> trelloLists2 = new ArrayList<>();
        trelloLists2.add(new TrelloList("1021", "List21", true));
        trelloLists2.add(new TrelloList("1022", "List22", false));
        trelloLists2.add(new TrelloList("1023", "List23", true));
        trelloLists2.add(new TrelloList("1024", "List24", false));

        List<TrelloList> trelloLists3 = new ArrayList<>();
        trelloLists3.add(new TrelloList("1001", "List31", false));
        trelloLists3.add(new TrelloList("1002", "List32", false));
        trelloLists3.add(new TrelloList("1003", "List33", true));
        trelloLists3.add(new TrelloList("1004", "List34", true));

        List<TrelloList> trelloLists4 = new ArrayList<>();
        trelloLists4.add(new TrelloList("1021", "List41", true));
        trelloLists4.add(new TrelloList("1022", "List42", true));
        trelloLists4.add(new TrelloList("1023", "List43", true));
        trelloLists4.add(new TrelloList("1024", "List44", true));

        TrelloBoard trelloBoard1 = new TrelloBoard("trb001", "Trello board 1", trelloLists1);
        TrelloBoard trelloBoard2 = new TrelloBoard("trb002", "Trello board 2", trelloLists2);
        TrelloBoard trelloBoard3 = new TrelloBoard("trb003", "Trello board 3", trelloLists3);
        TrelloBoard trelloBoard4 = new TrelloBoard("trb004", "Trello board 4", trelloLists4);

        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);
        trelloBoards.add(trelloBoard3);
        trelloBoards.add(trelloBoard4);

        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        assertEquals("trb004", trelloBoardsDto.get(3).getId());
        assertEquals("List22", trelloBoardsDto.get(1).getLists().get(1).getName());
        assertTrue(trelloBoardsDto.get(0).getLists().get(3).isClosed());
        assertEquals("1003", trelloBoardsDto.get(2).getLists().get(2).getId());

    }

    @Test
    public void mapToBoardAndMapToListTest() {
        //Given
        List<TrelloListDto> trelloListsDto1 = new ArrayList<>();
        trelloListsDto1.add(new TrelloListDto("1001", "List11", true));
        trelloListsDto1.add(new TrelloListDto("1002", "List12", false));
        trelloListsDto1.add(new TrelloListDto("1003", "List13", false));
        trelloListsDto1.add(new TrelloListDto("1004", "List14", true));

        List<TrelloListDto> trelloListsDto2 = new ArrayList<>();
        trelloListsDto2.add(new TrelloListDto("1021", "List21", true));
        trelloListsDto2.add(new TrelloListDto("1022", "List22", false));
        trelloListsDto2.add(new TrelloListDto("1023", "List23", true));
        trelloListsDto2.add(new TrelloListDto("1024", "List24", false));

        List<TrelloListDto> trelloListsDto3 = new ArrayList<>();
        trelloListsDto3.add(new TrelloListDto("1001", "List31", false));
        trelloListsDto3.add(new TrelloListDto("1002", "List32", false));
        trelloListsDto3.add(new TrelloListDto("1003", "List33", true));
        trelloListsDto3.add(new TrelloListDto("1004", "List34", true));

        List<TrelloListDto> trelloListsDto4 = new ArrayList<>();
        trelloListsDto4.add(new TrelloListDto("1021", "List41", true));
        trelloListsDto4.add(new TrelloListDto("1022", "List42", true));
        trelloListsDto4.add(new TrelloListDto("1023", "List43", true));
        trelloListsDto4.add(new TrelloListDto("1024", "List44", true));

        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("trb001", "Trello board 1", trelloListsDto1);
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("trb002", "Trello board 2", trelloListsDto2);
        TrelloBoardDto trelloBoardDto3 = new TrelloBoardDto("trb003", "Trello board 3", trelloListsDto3);
        TrelloBoardDto trelloBoardDto4 = new TrelloBoardDto("trb004", "Trello board 4", trelloListsDto4);

        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto1);
        trelloBoardDtos.add(trelloBoardDto2);
        trelloBoardDtos.add(trelloBoardDto3);
        trelloBoardDtos.add(trelloBoardDto4);

        //When
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);

        //Then
        assertEquals("trb004", trelloBoards.get(3).getId());
        assertEquals("List22", trelloBoards.get(1).getLists().get(1).getName());
        assertTrue(trelloBoards.get(0).getLists().get(3).isClosed());
        assertEquals("1003", trelloBoards.get(2).getLists().get(2).getId());

    }

    @Test
    public void TrelloFacadeFetchedTrelloBoarsTest() {
        //Given
        //When
        List<TrelloBoardDto> fetchedBoards = trelloFacade.fetchTrelloBoards();
        fetchedBoards.stream()
                .forEach(trelloBoardDto -> {
                    System.out.println("\nBoard name: " + trelloBoardDto.getName() +
                            ", board id: " + trelloBoardDto.getId() + ", lists: ");
                    trelloBoardDto.getLists().stream()
                            .forEach(trelloListDto -> System.out.println("  List id: " +
                                    trelloListDto.getId() + ", list name: \"" + trelloListDto.getName() +
                                    "\", is closed? - " + trelloListDto.isClosed()));
                });


        //Then
        assertEquals("Tablica startowa", fetchedBoards.get(1).getName());
        assertEquals("Thinks I am working on", fetchedBoards.get(0).getLists().get(1).getName());
        assertTrue(fetchedBoards.get(0).getLists().get(3).isClosed());
    }

    @Test
    public void mapToCardDtoTest() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Test card", "Test oriented card",
                "top", "5b225b815edff9c00bd6578c");
        //When
        TrelloCardDto mappedCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals("Test card", mappedCardDto.getName());
        assertEquals("Test oriented card", mappedCardDto.getDescription());
        assertEquals("top", mappedCardDto.getPos());
        assertEquals("5b225b815edff9c00bd6578c", mappedCardDto.getListId());
    }

    @Test
    public void mapToCardTest() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test card", "Test oriented description",
                "top", "5b225ae47018e9be2252a404");
        //When
        TrelloCard mappedCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals("Test card", mappedCard.getName());
        assertEquals("Test oriented description", mappedCard.getDescription());
        assertEquals("top", mappedCard.getPos());
        assertEquals("5b225ae47018e9be2252a404", mappedCard.getListId());
    }
}
