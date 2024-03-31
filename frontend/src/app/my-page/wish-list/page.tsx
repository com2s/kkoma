import TopBar from "@/components/my-page/wish-list/wish-list-bar";
import WishList from "@/components/my-page/wish-list/wish-list-content";
import Test from "@/components/my-page/wish-list/wish-list-content-test";
{/* UI테스트를 위한 컴포넌트 */}

export default function MyWishListPage() {
    return (
        <>
            <TopBar />
            <WishList />
            <Test /> {/* UI테스트를 위한 컴포넌트 */}
        </>
    );
}