package io.smallrye.reactive.groups;

import static io.smallrye.reactive.helpers.ParameterValidation.nonNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import org.reactivestreams.Publisher;

import io.smallrye.reactive.Uni;
import io.smallrye.reactive.adapt.converters.*;
import io.smallrye.reactive.operators.UniToPublisher;

public class UniAdapt<T> {

    private final Uni<T> upstream;

    public UniAdapt(Uni<T> upstream) {
        this.upstream = nonNull(upstream, "upstream");
    }

    /**
     * Transforms this {@link Uni} into a type using the provided converter.
     *
     * @param converter the converter function
     * @return an instance of O
     * @throws RuntimeException if the conversion fails.
     */
    public <R> R with(Function<Uni<T>, R> converter) {
        nonNull(converter, "converter");
        return converter.apply(upstream);
    }

    public CompletionStage<T> toCompletionStage() {
        return with(new ToCompletionStage<>());
    }

    public CompletableFuture<T> toCompletableFuture() {
        return with(new ToCompletableFuture<>());
    }

    public Publisher<T> toPublisher() {
        return UniToPublisher.adapt(upstream);
    }

}