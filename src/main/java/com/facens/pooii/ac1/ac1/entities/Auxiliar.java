package com.facens.pooii.ac1.ac1.entities;

public class Auxiliar {
    /*
    public TicketGetDTO getTickets(Long id){
        Optional<Event> op = repository.findById(id);
        Event ev = op.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found"));
        //List<Attend> freeTickets= ;
        //List<Attend> payedTickets;
        List<Ticket> tickets1 = repositoryTicket.freeTickets(id);
        return new TicketGetDTO(ev);
    }
    public TicketDTO insertTicket(TicketInsertDTO insertDTO, Long id){
        Optional<Event> ev = repository.findById(id);
        Event event = ev.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found"));

        Optional<Attend> at = repositoryAttend.findById(1L);
        Attend attend = at.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Attend not found"));
        Boolean disponivel = true;

        if(insertDTO.getType() == TicketType.FREETICKET){
            if(event.getFreeTickectsSelled()<event.getAmountFreeTickets()){
                disponivel = true;
            }
        }
        else
        {
            if(event.getPayedTickectsSelled()<event.getAmountPayedTickets()){
                disponivel = true;
            }
        }
        if(disponivel==true){
            Ticket ticket = new Ticket();
            ticket.setPrice(insertDTO.getPrice());
            ticket.setType(TicketType.FREETICKET);
            ticket.setEvent(event);
            ticket.setAttend(attend);

            event.addTicket(ticket);
            attend.addTicket(ticket);
            event=repository.save(event);
            attend = repositoryAttend.save(attend);
            //ticket = repositoryTicket.save(ticket);

            TicketDTO dto= new TicketDTO(ticket);
            System.out.println("Salvou.....");
            return dto;
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tickets unavailables!");
        }
    }
    
    Controller
    @PostMapping("/{id}/tickets ")
    public ResponseEntity<TicketDTO> insertTicket( @RequestBody TicketInsertDTO insertDTO, @PathVariable Long id){
		System.out.println("SalvouController.....");

        TicketDTO dto = service.insertTicket(insertDTO, id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
    */
}
