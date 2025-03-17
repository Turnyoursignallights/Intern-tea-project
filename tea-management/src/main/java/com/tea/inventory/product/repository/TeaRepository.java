package com.tea.inventory.product.repository;

//@Repository
public class TeaRepository {


//    private List<Tea> teas = new ArrayList<>();
//
//    // find all teas in the stock
//    public List<Tea> findAll(){
//        return teas;
//    }

//    @PostConstruct
//    private void init(){
//        teas.add(new Tea(1,
//                "Longjing Tea",
//                "Green Tea",
//                "One of the most famous Chinese green teas, known for its flat, smooth leaves and sweet, nutty flavor",
//                1000.0,
//                "China",
//                264800,
//                LocalDate.of(2023,10,1),
//                700.0));
//        teas.add(new Tea(2,
//                "English Breakfast Tea",
//                "Black Tea",
//                "A robust black tea blend, often made from Assam, Ceylon, and Kenyan teas.",
//                50.0,
//                "England",
//                100000,
//                LocalDate.of(2022,12,1),
//                10.0));
//    }


//    // find a tea by id
//    public Optional<Tea> findById(Integer id){
//        return teas.stream()
//                .filter(tea -> tea.id().equals(id))
//                .findFirst();
//
//    }
//// creating a new tea entity
//    public void create(Tea tea){
//        teas.add(tea);
//    }
//    // update the tea
//    public void update(Tea tea,Integer id){
//        Optional<Tea> existingtea = findById(id);
//        if(existingtea.isPresent()){
//            teas.set(teas.indexOf(existingtea.get()),tea);
//        }
//    }
//
//// delete the tea by id
//    public void delete(Integer id){
//        Optional<Tea> existingtea = findById(id);
//        if(existingtea.isPresent()){
//            teas.remove(existingtea.get());
//        }
//    }
}
