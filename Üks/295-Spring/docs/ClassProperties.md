# Entities structure (Without Getter, Setter, equals & hashCode)

## Person

- Person:

  - Fields:
  - id: Integer
  - email: String
  - password: String
  - events: Set<Event>
  - Constructors:
  - Person() {}
  - Person(Integer id) { this.id = id; }


- PersonRepository:
- PersonRequestDTO:
- PersonResponseDTO:
- PersonMapper:
- PersonService:

## Event
- Event:
  - id: Integer
  - name: String
  - date: LocalDate
  - description: String
  - owner: Person
  - tickets: Set<Ticket>


- EventRepository:
- EventDTO:
- EventMapper:
- EventService:

## Ticket

- Ticket:
  - id: Integer
  - amount: Integer
  - description: String
  - name: String
  - event: Event


- TicketRepository:
- TicketDTO:
- TicketMapper:
- TicketService: