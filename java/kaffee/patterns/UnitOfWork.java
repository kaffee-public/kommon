package kaffee.patterns;

import java.util.HashSet;
import java.util.Set;

/**
 * Skeleton for Unit of Work design pattern. Stores a type (T) and it's extends
 * (E).
 *
 * @author ahorvath
 * @param <T>
 */
public abstract class UnitOfWork<T> {

	private final Set<T> newObjects = new HashSet<>();
	private final Set<T> cleanObjects = new HashSet<>();
	private final Set<T> dirtyObjects = new HashSet<>();
	private final Set<T> deletedObjects = new HashSet<>();

	protected void markNew(T object) {
		if (newObjects.add(object)) {
			cleanObjects.remove(object);
			dirtyObjects.remove(object);
			deletedObjects.remove(object);
		}
	}

	protected void markClean(T object) {
		if (cleanObjects.add(object)) {
			newObjects.remove(object);
			dirtyObjects.remove(object);
			deletedObjects.remove(object);
		}
	}

	protected void markDirty(T object) {
		if (dirtyObjects.add(object)) {
			newObjects.remove(object);
			cleanObjects.remove(object);
			deletedObjects.remove(object);
		}
	}

	protected void markDeleted(T object) {
		if (deletedObjects.add(object)) {
			newObjects.remove(object);
			cleanObjects.remove(object);
			dirtyObjects.remove(object);
		}
	}

	@SuppressWarnings("unchecked")
	public <E extends T> Set<E> listNew(Class<E> clazz) {
		Set<E> ret = new HashSet<>();
		for (T newOne : newObjects) {
			if (newOne.getClass() == clazz) {
				ret.add((E) newOne);
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public <E extends T> Set<E> listDirty(Class<E> clazz) {
		Set<E> ret = new HashSet<>();
		for (T dirty : dirtyObjects) {
			if (dirty.getClass() == clazz) {
				ret.add((E) dirty);
			}
		}
		return ret;
	}

	public Set<T> listDirty() {
		return dirtyObjects;
	}

	public Set<T> listClean() {
		return cleanObjects;
	}

	@SuppressWarnings("unchecked")
	public <E extends T> Set<E> listDeleted(Class<E> clazz) {
		Set<E> ret = new HashSet<>();
		for (T deleted : deletedObjects) {
			if (deleted.getClass() == clazz) {
				ret.add((E) deleted);
			}
		}
		return ret;
	}

	public boolean hasNew() {
		return !newObjects.isEmpty();
	}

	public boolean hasDirty() {
		return !dirtyObjects.isEmpty();
	}

	public boolean hasDeleted() {
		return !deletedObjects.isEmpty();
	}

	public boolean isAllClean() {
		return newObjects.isEmpty() && dirtyObjects.isEmpty() && deletedObjects.isEmpty();
	}

	public boolean hasObject(T object) {
		return newObjects.contains(object) || dirtyObjects.contains(object) || deletedObjects.contains(object) || cleanObjects.contains(object);
	}

	/**
	 * Removes object.
	 *
	 * @param object
	 */
	public void remove(T object) {
		if (!dirtyObjects.remove(object) && !cleanObjects.remove(object) && !newObjects.remove(object)) {
			deletedObjects.remove(object);
		}
	}

	public void flush() {
		newObjects.clear();
		cleanObjects.clear();
		dirtyObjects.clear();
		deletedObjects.clear();
	}

	public <E extends T> Set<E> listNewAndDirty(Class<E> clazz) {
		Set<E> ret = listNew(clazz);
		ret.addAll(listDirty(clazz));
		return ret;
	}

	public void cleanUp() {
		cleanObjects.addAll(newObjects);
		cleanObjects.addAll(dirtyObjects);
		newObjects.clear();
		dirtyObjects.clear();
		deletedObjects.clear();
	}
}
