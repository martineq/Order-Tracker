package ordertracker.constants

class Enums {

    private static List<String> asList(Enum[] enums) {
        List<String> list = new ArrayList()
        enums.each { enumeration -> list.add(enumeration.toString())}
        return list
    }
}
