package edu.alenkin.repository;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.model.Label;

import java.util.List;

/**
 * Provides the ability to manipulate {@link edu.alenkin.model.Label label} entity in storage
 */
public interface LabelRepository extends Repository<Label, Long> {

    List<Label> getByPostId(Long id);
}
