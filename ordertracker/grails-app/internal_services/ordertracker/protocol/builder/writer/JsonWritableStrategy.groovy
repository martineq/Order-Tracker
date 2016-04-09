package ordertracker.protocol.builder.writer

import ordertracker.protocol.builder.Jsonable

class JsonWritableStrategy {

    private Collection<Jsonable> jsonables
    private Closure strategyMethod

    JsonWritableStrategy(Collection<Jsonable> jsonables, Closure strategyMethod) {
        this.jsonables = jsonables
        this.strategyMethod = strategyMethod
    }

    def writeJson() {
      this.strategyMethod(jsonables)
    }
}
