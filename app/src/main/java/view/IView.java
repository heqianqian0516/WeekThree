package view;

public interface IView<T> {
    void onRequestSuccess(T t);
    void onRequestFail(String error);

}
